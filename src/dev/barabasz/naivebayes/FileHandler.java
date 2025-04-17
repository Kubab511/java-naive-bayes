package dev.barabasz.naivebayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {
    private static List<Permutation> permutations = new ArrayList<>();
    private static List<String> data = new ArrayList<>();
    private static String[] headers;
    private static boolean permutationExists;
    private static float yes;
    private static float no;

    public static void readData(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        Logger.log("Opened file: " + filePath);
        String line;

        headers = bufferedReader.readLine().split(",");

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

        getTestData();

        buildFrequencyTable(data);

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

    private static void getTestData() {
        // Procent tak i nie
        float percentageYes = yes / (yes + no);
        float percentageNo = no / (yes + no);

        // Ilość tak i nie w zbiorze danych testowych, który ma rozmiar dokładnie 1/4 danych wejściowych
        double testYes = percentageYes * ((yes + no) * 0.25);
        double testNo = percentageNo * ((yes + no) * 0.25);

        Logger.log("Test data: yes: " + (int)testYes + ", no: " + (int)testNo);
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
}