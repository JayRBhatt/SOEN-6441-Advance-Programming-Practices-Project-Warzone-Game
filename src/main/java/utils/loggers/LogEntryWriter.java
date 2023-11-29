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
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 * @version 1.0.0
 */
public class LogEntryWriter implements Observer, Serializable {

    /**
     * File name for logger
     */
    private String l_FilenameString = "demo";
   /**
    * Constructs a new LogEntryWriter instance and clears all logs.
    */
    public LogEntryWriter() {
        clearAllLogs();
    }
   /**
    * Updates the observer with the provided message.
    *
    * @param p_s The message to update the observer with.
    */
    public void update(String p_s) {
        logMessage(p_s);
    }
   /**
 * Logs the provided message to a log file.
 *
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
  /**
 * Checks if the directory at the specified path exists, and creates it if it doesn't.
 *
 * @param path The path of the directory to be checked and created if necessary.
 */
    private void checkDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
/**
 * Clears all logs by deleting the log file at the specified path.
 */
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
