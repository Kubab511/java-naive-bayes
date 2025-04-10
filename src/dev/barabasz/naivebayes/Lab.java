package dev.barabasz.naivebayes;


import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Lab {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Naive Bayes Predictor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(0, 2));

            // Add feature input fields
            JLabel feature1Label = new JLabel("ParentHasJob:");
            JTextField feature1Field = new JTextField();
            frame.add(feature1Label);
            frame.add(feature1Field);

            JLabel feature2Label = new JLabel("FamilyIncomeHigh:");
            JTextField feature2Field = new JTextField();
            frame.add(feature2Label);
            frame.add(feature2Field);

            // Add more features as needed
            JLabel feature3Label = new JLabel("HasSiblings:");
            JTextField feature3Field = new JTextField();
            frame.add(feature3Label);
            frame.add(feature3Field);

            JLabel feature4Label = new JLabel("LivesNearSchool:");
            JTextField feature4Field = new JTextField();
            frame.add(feature4Label);
            frame.add(feature4Field);

            // Add predict button
            JButton predictButton = new JButton("Predict");
            frame.add(new JLabel()); // Empty cell for alignment
            frame.add(predictButton);

            predictButton.addActionListener(e -> {
                String feature1 = feature1Field.getText();
                String feature2 = feature2Field.getText();
                String feature3 = feature3Field.getText();
                String feature4 = feature4Field.getText();

                float probability = getProbability(feature1, feature2, feature3, feature4);

                JOptionPane.showMessageDialog(frame, "ChildIsEnrolled: " + String.format("%.2f", probability * 100) + "%");
            });

            // Display the frame
            frame.setVisible(true);
        });
    }



    private static float getProbability(String feature1, String feature2, String feature3, String feature4) {
        // ParentHasJob,FamilyIncomeHigh,HasSiblings,LivesNearSchool
        // no, yes, yes, yes = no=13, yes=2
        // yes, yes, yes, yes = no=8, yes=11
        // yes, no, no, yes = no=11, yes=3
        // total yes = 16
        // total no = 32
        // total = 48
        
        // Calculate likelihoods and prior probabilities
        float total = 48f;
        float totalYes = 16f;
        float totalNo = 32f;

        // Convert features to lowercase for consistency
        feature1 = feature1.toLowerCase();
        feature2 = feature2.toLowerCase();
        feature3 = feature3.toLowerCase();
        feature4 = feature4.toLowerCase();

        // Likelihoods for "yes"
        float likelihoodYes = 1f;
        if (feature1.equals("no") && feature2.equals("yes") && feature3.equals("yes") && feature4.equals("yes")) {
            likelihoodYes *= 2f / totalYes;
        } else if (feature1.equals("yes") && feature2.equals("yes") && feature3.equals("yes") && feature4.equals("yes")) {
            likelihoodYes *= 11f / totalYes;
        } else if (feature1.equals("yes") && feature2.equals("no") && feature3.equals("no") && feature4.equals("yes")) {
            likelihoodYes *= 3f / totalYes;
        } else {
            likelihoodYes *= 0f; // No matching data
        }

        // Likelihoods for "no"
        float likelihoodNo = 1f;
        if (feature1.equals("no") && feature2.equals("yes") && feature3.equals("yes") && feature4.equals("yes")) {
            likelihoodNo *= 13f / totalNo;
        } else if (feature1.equals("yes") && feature2.equals("yes") && feature3.equals("yes") && feature4.equals("yes")) {
            likelihoodNo *= 8f / totalNo;
        } else if (feature1.equals("yes") && feature2.equals("no") && feature3.equals("no") && feature4.equals("yes")) {
            likelihoodNo *= 11f / totalNo;
        } else {
            likelihoodNo *= 0f; // No matching data
        }

        // Posterior probabilities
        float posteriorYes = (likelihoodYes * (totalYes / total));
        float posteriorNo = (likelihoodNo * (totalNo / total));

        // Normalize to get the probability of "yes"
        return posteriorYes / (posteriorYes + posteriorNo);
    }
}