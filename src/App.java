import java.util.HashMap;
import java.util.Map;

import dev.barabasz.naivebayes.CalculateProbability;
import dev.barabasz.naivebayes.GUI;

public class App {
    public static void main(String[] args) throws Exception {
        // Map<String, String> input = new HashMap<>();
        // input.put("ParentEmployed", "Yes");
        // input.put("HasSiblings", "Yes");
        // input.put("ReceivesFinancialAid", "Yes");
        // input.put("LivesNearSchool", "Yes");

        CalculateProbability.loadFile("ChildIsEnrolled.csv");
        // System.out.println(CalculateProbability.predict(input));
        new GUI();
    }
}
