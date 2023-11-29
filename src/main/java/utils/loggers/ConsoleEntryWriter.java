package utils.loggers;

import java.io.Serializable;

import utils.maputils.Observer;

/**
 * A class to enable writing to console using the observer patter.
 *@author Jay Bhatt
 *@author Madhav Anadkat
 *@author Bhargav Fofandi
 */
public class ConsoleEntryWriter implements Observer, Serializable {

    /**
     * A function to update the string to observers
     * 
     * @param p_s the message to be updated
     */
    @Override
    public void update(String p_s) {
        System.out.println(p_s);
    }

    /**
     * A function to clear the logs
     */
    @Override
    public void clearAllLogs() {
        System.out.print("\033[H\033[2J");
    }
}
