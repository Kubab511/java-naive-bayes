package dev.barabasz.naivebayes;

import java.util.Arrays;

/**
 * The {@code Prediction} class provides functionality for making predictions based on the data loaded using the FileHandler class.
 * 
 * <p>Usage:
 * <ul>
 *   <li>Call {@link #predict(String)} to generate a prediction based on the data inputted.</li>
 *   <li>Use {@link #testData()} to run a test of the classifier and see how accurate it is.</li>
 * </ul>
 * 
 * 
 * <p>Example:
 * <pre>
 * {@code
 * Prediction.predict(data);
 * Prediction.testData();
 * }
 * </pre>
 * 
 * @author Jakub Barabasz - c23310371
 * @version 1.0.0
 */

public class Prediction {
    public static String predict(String data) {
        for (Permutation permutation : FileHandler.getPermutations()) {
            if (permutation.getData().equals(data)) {
                if (permutation.getYes() >= permutation.getNo()) {
                    float confidence = ((float) permutation.getYes() / ((float) permutation.getNo() + (float) permutation.getYes()) * 100);
                    Logger.log(data + " | yes | " + confidence);
                    return "Yes, with a confidence of: " + String.format("%.5f", confidence) + "%";
                } else {
                    float confidence = ((float) permutation.getNo() / ((float) permutation.getYes() + (float) permutation.getNo()) * 100);
                    Logger.log(data + " | no | " + confidence);
                    return "No, with a confidence of: " + String.format("%.5f", confidence) + "%";
                }
            }
        }

        return null;
    }

    public static float testData() {
        float total = FileHandler.getTestData().size();
        float correct = 0f;
        for (String row : FileHandler.getTestData()) {
            String[] tokens = row.split(",");
            String data = String.join(",", Arrays.copyOf(tokens, tokens.length - 1));

            for (Permutation permutation : FileHandler.getPermutations()) {
                if (permutation.getData().equals(data)) {
                    if (permutation.getYes() >= permutation.getNo()) {
                        if (tokens[tokens.length - 1].strip().equals("yes")) {
                            correct += 1f;
                            Logger.log("correct, yes");
                        }
                    } else {
                        if (tokens[tokens.length - 1].strip().equals("no")) {
                            correct += 1f;
                            Logger.log("correct, no");
                        }
                    }
                }
            }
        }

        return (correct/total) * 100f;
    }
}