package dev.barabasz.naivebayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {
    private static List<Permutation> permutations = new ArrayList<>();
    private static String[] headers;
    private static boolean permutationExists;
    private static int yes = 0;
    private static int no = 0;

    public static void buildFrequencyTable(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        Logger.log("Opened file: " + filePath);

        String line;
        headers = bufferedReader.readLine().split(",");

        while ((line = bufferedReader.readLine()) != null) {
            addRow(line);
        }

        logFrequencyTable();
        getTotalRows();

        bufferedReader.close();
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

    public static String[] getHeaders() {
        return headers;
    }

    private static void getTotalRows() {
        for (Permutation permutation : FileHandler.permutations) {
            yes += permutation.getYes();
            no += permutation.getNo();
        }

        Logger.log("Total rows: yes: " + yes + ", no: " + no);
        Logger.log("Total rows: " + (yes + no));
    }

    public static void getTrainingData() {

    }
}