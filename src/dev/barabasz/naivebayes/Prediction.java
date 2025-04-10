package dev.barabasz.naivebayes;

public class Prediction {
    public static String predict(String data) {
        // data must be given in the format xx,xx,xx,xx
        for (Permutation permutation : FileHandler.getPermutations()) {
            if (permutation.getData().equals(data)) {
                if (permutation.getYes() > permutation.getNo()) {
                    float confidence = ((float) permutation.getYes() / (float) (permutation.getNo() + (float) permutation.getYes()) * 100);
                    Logger.log(data + " | yes | " + confidence);
                    return "Yes, with a confidence of: " + confidence + "%";
                } else {
                    float confidence = ((float) permutation.getNo() / (float) (permutation.getYes() + (float) permutation.getNo()) * 100);
                    Logger.log(data + " | no | " + confidence);
                    return "No, with a confidence of: " + confidence + "%";
                }
            }
        }

        return null;
    }
}
