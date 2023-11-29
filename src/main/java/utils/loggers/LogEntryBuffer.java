package utils.loggers;

import utils.maputils.Observable;
import utils.maputils.Observer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class records all actions within the game as observable events.
 * It enables observers to be notified of game actions.
 * @author Jay Bhatt
 * @author Madhav Anadkat
 * @author Bhargav Fofandi
 */
public class LogEntryBuffer implements Observable, Serializable {
    private static volatile LogEntryBuffer Logger; // Made volatile for thread safety
    private List<Observer> d_ObserverList = new ArrayList<>();
  /**
   * Private constructor to prevent instantiation
   */
    private LogEntryBuffer() {
        
    }
 /**
 * Gets an instance of the LogEntryBuffer class following the Singleton pattern.
 *
 * @return An instance of the LogEntryBuffer class.
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
 * Logs an action and notifies observers with the provided message.
 *
 * @param p_s The message to be logged and sent to observers.
 */
    public void logAction(String p_s) {
        notifyObservers(p_s);
    }
/**
 * Clears all observers
 */
    public void clear() {
        clearAllObservers();
    }
/**
 * Notifies all registered observers with the provided message.
 *
 * @param p_s The message to be sent to observers.
 */
    @Override
    public void notifyObservers(String p_s) {
        d_ObserverList.forEach(p_observer -> {
            if (p_observer != null) {
                p_observer.update(p_s);
            }
        });
    }
/**
 * Adds a new observer.
 *
 * @param p_Observer The observer to be added.
 */
    @Override
    public void addNewObserver(Observer p_Observer) {
        if (p_Observer != null) {
            this.d_ObserverList.add(p_Observer);
        }
    }
/**
 * Clears all observers
 */
    @Override
    public void clearAllObservers() {
        d_ObserverList.forEach(observer -> {
            if (observer != null) {
                observer.clearAllLogs();
            }
        });
    }
}
