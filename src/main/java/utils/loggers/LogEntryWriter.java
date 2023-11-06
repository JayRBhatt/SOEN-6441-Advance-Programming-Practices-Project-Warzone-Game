package utils.loggers;

import utils.maputils.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Observes LogEntryBuffer and records the actions to a log file.
 */
public class LogEntryWriter implements Observer {

    /**
     * Receives updates from the observed subject and logs the information.
     *
     * @param p_message the information to be logged.
     */
    public void update(String p_message) {
        logMessage(p_message);
    }

    /**
     * Logs game actions to a specified log file.
     *
     * @param p_logMessage the content to be logged.
     */
    public void logMessage(String p_logMessage) {
        PrintWriter l_writer = null;
        String l_fileName = "demo";
        try {
            l_writer = new PrintWriter(new BufferedWriter(new FileWriter("logFiles/" + l_fileName + ".log", true)));
            l_writer.println(p_logMessage);
        } catch (Exception l_exception) {
            System.out.println(l_exception.getMessage());
        } finally {
            if (l_writer != null) {
                l_writer.close();
            }
        }
    }
}
