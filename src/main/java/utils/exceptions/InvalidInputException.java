package utils.exceptions;

/**
 * This class represents an exception for handling invalid console commands.
 * It provides a user-friendly default message if none is provided.
 * 
 * @author Madhav Anadkat
 */
public class InvalidInputException extends Exception {
    // a message
    String d_Message = "Oh C'mon you can't be using this command in this phase";

    /**
     * Constructor that calls the Parent class
     * 
     */
    public InvalidInputException() {
        super();
    }

    /**
     * Method that passes the display message to the parent class for its execution
     * 
     * @param p_displayMessage the message to be displayed once the exception occurs
     */
    public InvalidInputException(String p_displayMessage) {
        super(p_displayMessage);
    }

    /**
     * An ovveridden Method for compiler to get a message returned
     * 
     * @return d_Messgae the message to get displayed
     */
    @Override
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}