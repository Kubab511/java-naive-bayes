import dev.barabasz.naivebayes.GUI;
import dev.barabasz.naivebayes.Logger;

/**
 * The App class serves as the entry point for the application.
 * It initializes the logging system, logs the application start event,
 * and creates an instance of the GUI class to launch the graphical user interface.
 *
 * @author Jakub Barabasz - c23310371
 * @version 1.0.0
 */
public class App {
    public static void main(String[] args) throws Exception {
        Logger.initLog();
        Logger.log("Application started");
        new GUI();
    }
}