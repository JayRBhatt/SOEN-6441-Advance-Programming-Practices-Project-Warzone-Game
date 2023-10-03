package utils.exceptions;

public class InvalidInputException  extends Exception
{
    String d_Message = "Oh C'mon you can't be using this command in this phase";

    public InvalidInputException()
    {
        super();
    }

    public InvalidInputException(String p_displayMessage)
    {
        super(p_displayMessage);
    }

    @Override
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        return d_Message;
    }
}