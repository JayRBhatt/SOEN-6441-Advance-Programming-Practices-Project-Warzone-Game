package utils.loggers;

import utils.maputils.Observable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * This class records all actions within the game as observable events.
 * It enables observers to be notified of game actions.
 */
public class LogEntryBuffer implements Observable {
    /**
     * Instance of {@code LogEntryWriter} to handle log writing.
     */
    private LogEntryWriter d_logEntryWriter = new LogEntryWriter(); // Observer instance for logging.

    /**
     * Receives game action information and notifies the observer.
     *
     * @param p_actionInfo The information about the game action to log.
     */
    public void logAction(String p_actionInfo) {
        notifyObservers(p_actionInfo);
    }

    /**
     * Notifies the observer with the given message.
     *
     * @param p_message The message to notify the observer with.
     */
    public void notifyObservers(String p_message) {
        d_logEntryWriter.logMessage(p_message);
    }

    /**
     * Prepares a new log file by clearing any previous content.
     * This is typically used before the start of a new game.
     */
    public void prepareNewLogFile() {
        PrintWriter l_writer = null;
        String l_fileName = "game_log";
        try {
            l_writer = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_fileName + ".log", false)));
            l_writer.print(""); // Clear the file content.
        } catch (Exception ex) {
            // Exception handling logic should be here
        } finally {
            if (l_writer != null) {
                l_writer.close(); // Close the writer to prevent resource leak.
            }
        }
    }
}
