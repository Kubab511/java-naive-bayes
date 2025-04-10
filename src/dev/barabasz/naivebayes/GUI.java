package dev.barabasz.naivebayes;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.FlowLayout;
import java.io.IOException;

public class GUI extends JFrame {
    private JFileChooser jFileChooser;
    private FileNameExtensionFilter filter;

    public GUI() {
        super("Naive Bayes Classifier Calculator");
        setLayout(new FlowLayout());

        JButton openButton = new JButton("Open File");
        JLabel[] featureLabels = new JLabel[4];
        javax.swing.JTextField[] featureInputs = new javax.swing.JTextField[4];
        for (int i = 0; i < 4; i++) {
            featureLabels[i] = new JLabel("feature " + (i + 1) + ": ");
            featureInputs[i] = new javax.swing.JTextField(10);
            add(featureLabels[i]);
            add(featureInputs[i]);
        }

        JButton addRowButton = new JButton("Add Row");
        addRowButton.addActionListener(e -> {
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
                String rowData = "";
                for (int i = 0; i < headers.length; i++) {
                    if (i == headers.length - 1) {
                        rowData += inputFields[i].getText();
                    } else {
                        rowData += inputFields[i].getText() + ",";
                    }  
                }
                FileHandler.addRow(rowData);
                Logger.log("Added row: " + rowData);
                FileHandler.logFrequencyTable();
                addRowFrame.dispose();
            });

            addRowFrame.add(addButton);
            addRowFrame.setVisible(true);
        });

        add(addRowButton);

        openButton.addActionListener(e -> {
            jFileChooser = new JFileChooser();
            filter = new FileNameExtensionFilter("CSV Files", "csv");
            jFileChooser.setFileFilter(filter);
            int returnVal = jFileChooser.showOpenDialog(rootPane);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    FileHandler.buildFrequencyTable(jFileChooser.getSelectedFile().getAbsolutePath());
                    String[] headers = FileHandler.getHeaders();
                    for (int i = 0; i < featureLabels.length && i < headers.length; i++) {
                        featureLabels[i].setText(headers[i] + ": ");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(openButton);

        JButton predictButton = new JButton("Predict");
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

        add(predictButton);

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
