package dev.barabasz.naivebayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The FileHandler class is responsible for handling file operations and data processing
 * for a Naive Bayes classifier. It reads data from a CSV file, splits it into training
 * and test datasets, and builds a frequency table for the classifier.
 * 
 * <p>Key functionalities include:
 * <ul>
 *   <li>Reading data from a file and parsing it into a list of strings.</li>
 *   <li>Splitting the data into training and test datasets based on a 75-25% ratio.</li>
 *   <li>Building a frequency table for permutations of data attributes.</li>
 *   <li>Logging the frequency table and other relevant information.</li>
 * </ul>
 * 
 * <p>Dependencies:
 * <ul>
 *   <li>Logger: Used for logging messages and debugging information.</li>
 *   <li>Permutation: Represents a combination of data attributes and their frequency counts.</li>
 * </ul>
 * 
 * <p>Usage:
 * <pre>
 * {@code
 * FileHandler.readData("path/to/data.csv");
 * List<Permutation> permutations = FileHandler.getPermutations();
 * List<String> testData = FileHandler.getTestData();
 * }
 * </pre>
 *
 * @author Jakub Barabasz - c23310371
 * @version 1.0.0
 */
public class FileHandler {
    private static List<Permutation> permutations = new ArrayList<>();
    private static List<String> data = new ArrayList<>();
    private static List<String> testData = new ArrayList<>();
    private static List<String> trainingData = new ArrayList<>();
    private static String[] headers;
    private static boolean permutationExists;
    private static float yes;
    private static float no;

    public static void readData(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        Logger.log("Opened file: " + filePath);
        String line;

        headers = bufferedReader.readLine().split(",");

        // Read input file line by line, count total yes and no and populate the data List
        while ((line = bufferedReader.readLine()) != null) {
            data.add(line);
            if (line.split(",")[line.split(",").length - 1].equals("yes")) {
                yes += 1f;
            } else {
                no += 1f;
            }
        }

        Logger.log("Read file: " + filePath + ", lines: " + data.size());
        Logger.log("yes: " + yes + ", no: " + no);

        buildData();

        buildFrequencyTable(trainingData);

        bufferedReader.close();
    }

    private static void buildFrequencyTable(List<String> data) {
        Logger.log("Building frequency table");

        for (int i = 0; i < data.size(); i++) {
            addRow(data.get(i));
        }

        logFrequencyTable();
    }

    public static void logFrequencyTable() {
        String logFreqTable = "Frequency table for: " + headers[headers.length-1] + "\n";
        String freqTable = String.join(",", Arrays.copyOfRange(headers, 0, headers.length - 1));
        logFreqTable += freqTable + "\n";
        for (Permutation permutation : permutations) {
            String paddedData = String.format("%-16s", permutation.getData());
            logFreqTable += paddedData + " | yes: " + (int)permutation.getYes() + ", no: " + (int)permutation.getNo() + "\n";
        }

        Logger.log(logFreqTable);
    }

    private static void buildData() {
        float percentageYes = yes / (yes + no);
        float percentageNo = no / (yes + no);

        // Count how many yes and no permutations need to go into the test data which is 1/4 of the total data given
        int testYes = (int)(percentageYes * ((yes + no) * 0.25));
        int testNo = (int)(percentageNo * ((yes + no) * 0.25));

        Logger.log("Test data: yes: " + testYes + ", no: " + testNo);

        // Shuffle the data to randomize which permutations are chosen (this may slightly change individual percentages in the frequency table as different rows are chosen each time)
        Collections.shuffle(data);

        for (int i = 0; i < data.size(); i++) {
            String[] tokens = data.get(i).split(",");
            if (tokens[tokens.length-1].equals("yes") && testYes > 0) {
                testData.add(data.get(i));
                testYes--;
            } else if (tokens[tokens.length-1].equals("no") && testNo > 0) {
                testData.add(data.get(i));
                testNo--;
            } else {
                trainingData.add(data.get(i));
            }
        }

        Logger.log("Test data size: " + testData.size() + ", training data size: " + trainingData.size());
        Logger.log("Test data: " + testData);
    }

    public static void addRow(String row) {
        permutationExists = false;

        String[] tokens = row.split(",");
        String data = String.join(",", Arrays.stream(tokens, 0, tokens.length-1).toArray(String[]::new));

        for (Permutation permutation : permutations) {
            if (permutation.getData().equals(data)) {
                if (tokens[tokens.length-1].equals("yes")) {
                    permutation.setYes(permutation.getYes() + 1f);
                    permutationExists = true;
                    break;
                } else if (tokens[tokens.length-1].equals("no")) {
                    permutation.setNo(permutation.getNo() + 1f);
                    permutationExists = true;
                    break;
                }
            }
        }

        if (!permutationExists) {
            if (tokens[4].equals("yes")) {
                permutations.add(new Permutation(data, 1f, 0f));
            } else if (tokens[4].equals("no")) {
                permutations.add(new Permutation(data, 0f, 1f));
            }
        }
    }

    public static List<Permutation> getPermutations() {
        return permutations;
    }

    public static List<String> getTestData() {
        return testData;
    }

    public static String[] getHeaders() {
        return headers;
    }
}