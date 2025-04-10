package dev.barabasz.naivebayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static List<Permutation> permutations = new ArrayList<>();
    private static String[] headers;
    private static boolean permutationExists;

    public static void buildFrequencyTable(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        Logger.log("Opened file: " + filePath);

        String line;
        headers = bufferedReader.readLine().split(",");

        while ((line = bufferedReader.readLine()) != null) {
            addRow(line);
        }

        logFrequencyTable();

        bufferedReader.close();
    }

    public static void logFrequencyTable() {
        String logFreqTable = "Frequency tables for: " + headers[4] + "\n";
        logFreqTable += headers[0] + ", " + headers[1] + ", " + headers[2] + ", " + headers[3] + "\n";
        for (Permutation permutation : permutations) {
            String paddedData = String.format("%-16s", permutation.getData());
            logFreqTable += paddedData + " | yes: " + permutation.getYes() + ", no: " + permutation.getNo() + "\n";
            // logFreqTable += permutation.getData() + " | yes: " + permutation.getYes() + ", no: " + permutation.getNo() + "\n";
        }

        Logger.log(logFreqTable);
    }

    public static void addRow(String row) {
        permutationExists = false;

        String[] tokens = row.split(",");
        String data = String.join(",", tokens[0], tokens[1], tokens[2], tokens[3]);

        for (Permutation permutation : permutations) {
            if (permutation.getData().equals(data)) {
                if (tokens[4].equals("yes")) {
                    permutation.setYes(permutation.getYes() + 1);
                    permutationExists = true;
                    break;
                } else if (tokens[4].equals("no")) {
                    permutation.setNo(permutation.getNo() + 1);
                    permutationExists = true;
                    break;
                }
            }
        }

        if (!permutationExists) {
            if (tokens[4].equals("yes")) {
                permutations.add(new Permutation(data, 1, 0));
            } else if (tokens[4].equals("no")) {
                permutations.add(new Permutation(data, 0, 1));
            }
            
        }
    }

    public static List<Permutation> getPermutations() {
        return permutations;
    }

    public static String[] getHeaders() {
        return headers;
    }
}