package dev.barabasz.naivebayes;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FlowLayout;
import java.io.IOException;

/**
 * The GUI class represents the graphical user interface for the Naive Bayes Classifier Calculator.
 * It extends the JFrame class and provides functionality for interacting with the application,
 * including loading data, adding rows, making predictions, and testing data accuracy.
 * 
 * Features:
 * - Allows users to load a CSV file containing data.
 * - Dynamically updates feature labels based on the loaded data headers.
 * - Provides input fields for entering feature values for prediction.
 * - Enables users to add new rows of data to the dataset.
 * - Displays prediction results based on the entered feature values.
 * - Tests the accuracy of the classifier on the dataset.
 * 
 * Components:
 * - Buttons for opening files, adding rows, making predictions, and testing data.
 * - Labels and text fields for displaying and entering feature values.
 * - Dialogs for displaying results and error messages.
 * 
 * Dependencies:
 * - FileHandler: Handles file operations such as reading data, adding rows, and logging frequency tables.
 * - Prediction: Provides methods for making predictions and testing data accuracy.
 * - Logger: Logs application events and actions.
 * 
 * Usage:
 * - Instantiate the GUI class to launch the application.
 * - Interact with the buttons and input fields to perform various operations.
 * 
 * Note:
 * - Ensure that the FileHandler and Prediction classes are implemented and accessible.
 * - The application expects CSV files with appropriate headers for feature labels.
 * 
 * @author Jakub Barabasz - c23310371
 * @version 1.0.0
 */
public class GUI extends JFrame {
    private JFileChooser jFileChooser;
    private FileNameExtensionFilter filter;

    @SuppressWarnings("unused")
    public GUI() {
        super("Naive Bayes Classifier Calculator");

        JButton openButton = new JButton("Open File");
        JLabel[] featureLabels = new JLabel[4];
        javax.swing.JTextField[] featureInputs = new javax.swing.JTextField[4];
        setLayout(null);

        for (int i = 0; i < 4; i++) {
            featureLabels[i] = new JLabel("feature " + (i + 1) + ": ");
            featureLabels[i].setBounds(20, 20 + (i * 40), 140, 30);
            add(featureLabels[i]);

            featureInputs[i] = new javax.swing.JTextField(10);
            featureInputs[i].setBounds(145, 20 + (i * 40), 200, 30);
            add(featureInputs[i]);
        }

        JButton addRowButton = new JButton("Add Row");
        JButton predictButton = new JButton("Predict");
        JButton testButton = new JButton("Test Data");

        openButton.setBounds(25, 200, 100, 30);
        addRowButton.setBounds(135, 200, 100, 30);
        predictButton.setBounds(245, 200, 100, 30);
        testButton.setBounds(355, 200, 100, 30);

        add(openButton);
        add(addRowButton);
        add(predictButton);
        add(testButton);

        addRowButton.addActionListener(e -> {
            try {
                JFrame addRowFrame = new JFrame("Add Row");
                addRowFrame.setLayout(new FlowLayout());
                addRowFrame.setSize(400, 300);
    
                String[] headers = FileHandler.getHeaders();
                JLabel[] inputLabels = new JLabel[headers.length];
                javax.swing.JTextField[] inputFields = new javax.swing.JTextField[headers.length];
    
                for (int i = 0; i < headers.length; i++) {
                    inputLabels[i] = new JLabel(headers[i] + ": ");
                    inputFields[i] = new javax.swing.JTextField(10);
                    addRowFrame.add(inputLabels[i]);
                    addRowFrame.add(inputFields[i]);
                }

                JButton addButton = new JButton("Add");
                addButton.addActionListener(addEvent -> {
                    try {
                        String rowData = String.join(",", 
                            java.util.stream.IntStream.range(0, headers.length)
                            .mapToObj(i -> inputFields[i].getText())
                            .toArray(String[]::new)
                        );
                        FileHandler.addRow(rowData);
                        Logger.log("Added row: " + rowData);
                        FileHandler.logFrequencyTable();
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    
                    addRowFrame.dispose();
                });

                addRowFrame.add(addButton);
                addRowFrame.setVisible(true);
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "You must load data before adding rows to it.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });

        openButton.addActionListener(e -> {
            jFileChooser = new JFileChooser();
            filter = new FileNameExtensionFilter("CSV Files", "csv");
            jFileChooser.setFileFilter(filter);
            int returnVal = jFileChooser.showOpenDialog(rootPane);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileHandler.readData(jFileChooser.getSelectedFile().getAbsolutePath());
                String[] headers = FileHandler.getHeaders();
                for (int i = 0; i < featureLabels.length && i < headers.length; i++) {
                    featureLabels[i].setText(headers[i] + ": ");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            }
        });

        predictButton.addActionListener(e -> {
            StringBuilder inputData = new StringBuilder();
            boolean allFilled = true;

            for (int i = 0; i < featureInputs.length; i++) {
                String text = featureInputs[i].getText().trim();
                if (text.isEmpty()) {
                    allFilled = false;
                    break;
                }

                inputData.append(text);

                if (i < featureInputs.length - 1) {
                    inputData.append(",");
                }
            }

            if (!allFilled) {
                javax.swing.JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                String prediction = Prediction.predict(inputData.toString());
                javax.swing.JOptionPane.showMessageDialog(this, "Prediction: " + prediction, "Prediction Result", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });

        testButton.addActionListener(e -> {
            try {
                if (FileHandler.getPermutations().isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(this, "No data loaded.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
                float result = Prediction.testData();
                javax.swing.JOptionPane.showMessageDialog(this, "Test Result: " + String.format("%.2f", result) + "% of the predictions were correct.", "Test Data Result", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "An error occurred while testing data: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        setResizable(false);
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
