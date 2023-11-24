package utils.maputils;

/**
 * An interface for implementation of Observable with a notifyObservers
 * function.
 * 
 * @author Jay Bhatt
 *
 */
public interface Observable {

    /**
     * A function to send a message/notification to Observer.
     * 
     * @param p_s the observable
     */
    public void notifyObservers(String p_s);

    /**
     * add observer
     *
     * @param p_Observer observer object
     */
    public void addNewObserver(Observer p_Observer);

    /**
     * clear observer
     */
    public void clearAllObservers();
}
