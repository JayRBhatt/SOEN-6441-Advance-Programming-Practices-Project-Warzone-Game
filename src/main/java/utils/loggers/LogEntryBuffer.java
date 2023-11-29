package utils.loggers;

import utils.maputils.Observable;
import utils.maputils.Observer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class records all actions within the game as observable events.
 * It enables observers to be notified of game actions.
 */
public class LogEntryBuffer implements Observable, Serializable {
    private static volatile LogEntryBuffer Logger; // Made volatile for thread safety
    
    /**
     * List of observers
     */
    private List<Observer> d_ObserverList = new ArrayList<>();

    private LogEntryBuffer() {
        // Private constructor to prevent instantiation
    }

	 /**
	 * Retrieves the singleton instance of LogEntryBuffer in a thread-safe manner.
	 * @return The instance of LogEntryBuffer.
	 */
    public static LogEntryBuffer getInstance() {
        if (Logger == null) {
            synchronized (LogEntryBuffer.class) {
                if (Logger == null) {
                    Logger = new LogEntryBuffer();
                }
            }
        }
        return Logger;
    }

    /**
     * Logs an action and notifies observers
     * @param p_s The message for the observers.
     */
    public void logAction(String p_s) {
        notifyObservers(p_s);
    }

    /**
     * Clears all observers associated with the subject.
     */
    public void clear() {
        clearAllObservers();
    }

    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> {
            if (p_observer != null) {
                p_observer.update(p_s);
            }
        });
    }

    @Override
    public void addNewObserver(Observer p_Observer) {
        if (p_Observer != null) {
            this.d_ObserverList.add(p_Observer);
        }
    }

    @Override
    public void clearAllObservers() {
        d_ObserverList.forEach(observer -> {
            if (observer != null) {
                observer.clearAllLogs();
            }
        });
    }
}
