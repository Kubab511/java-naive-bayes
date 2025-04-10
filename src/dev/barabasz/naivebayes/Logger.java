package dev.barabasz.naivebayes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
    private static String logPath;

    public static void initLog() {
        String appDataPath = System.getenv("APPDATA");
        if (appDataPath == null) {
            throw new IllegalStateException("APPDATA environment variable is not set.");
        }

        String logDirPath = appDataPath + File.separator + "dev.barabasz.naivebayes";
        File logDir = new File(logDirPath);
        if (!logDir.exists() && !logDir.mkdirs()) {
            throw new IllegalStateException("Failed to create log directory: " + logDirPath);
        }

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File logFile = new File(logDir, date + ".log");
        int counter = 1;

        while (logFile.exists()) {
            logFile = new File(logDir, date + "_" + counter + ".log");
            counter++;
        }

        try {
            if (logFile.createNewFile()) {
                logPath = logFile.getAbsolutePath();
            } else {
                throw new IOException("Failed to create log file: " + logFile.getAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing log file", e);
        }
    }

    public static String getLogPath() {
        return logPath;
    }

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(logPath, true)) {
            writer.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to log file", e);
        }
    }
}

