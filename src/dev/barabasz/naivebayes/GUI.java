package dev.barabasz.naivebayes;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;

public class GUI extends JFrame {
    private JFileChooser jFileChooser;
    private FileNameExtensionFilter filter;

    public GUI() {
        super("Naive Bayes Classifier Calculator");
        setLayout(new FlowLayout());
        JButton openButton = new JButton("Open File");
        openButton.addActionListener(e -> {
            jFileChooser = new JFileChooser();
            filter = new FileNameExtensionFilter("CSV Files", "csv");
            jFileChooser.setFileFilter(filter);
            int returnVal = jFileChooser.showOpenDialog(rootPane);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(jFileChooser.getSelectedFile().getName());
            }
        });
        add(openButton);

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
