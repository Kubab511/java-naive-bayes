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

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
