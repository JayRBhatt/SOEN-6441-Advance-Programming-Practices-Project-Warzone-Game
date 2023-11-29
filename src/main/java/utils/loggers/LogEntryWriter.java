package utils.loggers;

import utils.maputils.Observer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * Observes LogEntryBuffer and records the actions to a log file.
 */
public class LogEntryWriter implements Observer, Serializable {

    /**
     * File name for logger
     */
    private String l_FilenameString = "demo";

    /**
     * Constructs a LogEntryWriter and clears all existing logs.
     */
    public LogEntryWriter() {
        clearAllLogs();
    }

    /**
     * Updates the observer with the provided message 
     * @param p_s The message to be logged.
     */
    public void update(String p_s) {
        logMessage(p_s);
    }
    
    /**
     * Logs a message to a file with the provided content.
     * @param p_str The message to be logged.
     */

    public void logMessage(String p_str) {
        String filePath = "logFiles/" + l_FilenameString + ".log";
        try (PrintWriter l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            checkDirectory("logFiles");
            l_WriteData.println(p_str);
        } catch (IOException p_Exception) {
            System.out.println("Error writing to log file: " + p_Exception.getMessage());
        }
    }

    private void checkDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public void clearAllLogs() {
        String filePath = "logFiles/" + l_FilenameString + ".log";
        checkDirectory("logFiles");
        File l_File = new File(filePath);
        if (l_File.exists()) {
            l_File.delete();
        }
    }
}
