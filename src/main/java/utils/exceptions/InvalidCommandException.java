package utils.exceptions;

/**
 * This class represents an exception for handling invalid console commands.
 * It provides a user-friendly default message if none is provided.
 * 
 * @author Madhav Anadkat
 */
public class InvalidCommandException extends Exception {
    private static final String d_DefaultMessage = "Invalid command. Type 'help' to learn more.";

    /**
     * Constructs a ValidationException with the default message.
     */
    public InvalidCommandException() {
        super(d_DefaultMessage);
    }

    /**
     * Constructs a ValidationException with a custom message.
     * 
     * @param p_message The custom exception message.
     */
    public InvalidCommandException(String p_message) {
        super(p_message);
    }

    /**
     * Returns the exception message, or the default message if no custom message is
     * provided.
     * 
     * @return The exception message.
     */
    @Override
    public String getMessage() {
        String customMessage = super.getMessage();
        return (customMessage != null) ? customMessage : d_DefaultMessage;
    }
}