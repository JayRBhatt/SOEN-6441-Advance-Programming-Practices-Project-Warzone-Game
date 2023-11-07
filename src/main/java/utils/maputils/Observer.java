package utils.maputils;

/**
 * An interface for implementation of Observer with an update function
 * 
 * @author
 */
public interface Observer {

    /**
     * Function to update the message for the observer
     * 
     * @param p_s the message to be updated
     */
    void update(String p_s);
}
