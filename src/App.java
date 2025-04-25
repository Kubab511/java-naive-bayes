import dev.barabasz.naivebayes.GUI;
import dev.barabasz.naivebayes.Logger;

public class App {
    public static void main(String[] args) throws Exception {
        Logger.initLog();
        Logger.log("Application started");
        new GUI();
    }
}