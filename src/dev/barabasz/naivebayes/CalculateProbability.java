package dev.barabasz.naivebayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateProbability {
    private static List<Map<String, String>> data = new ArrayList<>();

    public static void loadFile(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String line;
        String[] headers = bufferedReader.readLine().split(",");

        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> row = new HashMap<>();

            for (int i=0; i < headers.length; i++) {
                row.put(headers[i], values[i]);
            }
            
            data.add(row);
        }
        bufferedReader.close();
    }

    public static String predict(Map<String, String> input) {
        String[] labels = {"Yes", "No"};

        double maxProbability = -1;
        String bestLabel = null;

        for (String label : labels) {
            double probability = getPrior(label);

            for (String feature : input.keySet()) {
                probability *= getLikelihood(feature, input.get(feature), label);
            }

            if (probability > maxProbability) {
                maxProbability = probability;
                bestLabel = label;
            }
        }

        return bestLabel;
    }

    private static double getPrior(String label) {
        if (data.isEmpty()) {
            return (double) 0;
        }
        long count = data.stream()
                         .filter(row -> row.get("ChildIsEnrolled").equals(label))
                         .count();
        return (double) count / data.size();
    }
    
    private static double getLikelihood(String feature, String value, String label) {
        long labelCount = data.stream()
                              .filter(row -> row.get("ChildIsEnrolled").equals(label))
                              .count();
        long matchCount = data.stream()
                              .filter(row -> row.get("ChildIsEnrolled").equals(label) && row.get(feature).equals(value))
                              .count();
        
        return (matchCount + 1.0) / (labelCount + 2.0);
    }
}
