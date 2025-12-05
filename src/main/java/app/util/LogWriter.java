package app.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {

    private static final String BASE_DIR = "logs";

    public static synchronized void log(String scenario, String line) {
        if (scenario == null || scenario.trim().isEmpty()) {
            scenario = "default";
        }
        String fileName = scenario + ".log";
        File dir = new File(BASE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File logFile = new File(dir, fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Also print to console
        System.out.println(line);
    }

    public static synchronized void clear(String scenario) {
        if (scenario == null || scenario.trim().isEmpty()) {
            scenario = "default";
        }
        File dir = new File(BASE_DIR);
        if (!dir.exists()) {
            return;
        }
        File logFile = new File(dir, scenario + ".log");
        if (logFile.exists()) {
            logFile.delete();
        }
    }
}
