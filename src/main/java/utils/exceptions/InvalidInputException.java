package utils.exeptions;

public class InvalidExecutionException Exception extends Exception
{
    String d_Message = "Oh C'mon you can't be using this command in this phase";

    public InvalidExecutionException()
    {
        super();
    }

    public InvalidExecutionException(String p_displayMessage)
    {
        super(p_displayMessage);
    }

    @Override
    public String getDisplayMessage()
    {
        if()
        {
            if(super.getDisplayMessage!=null)
            {
                return super.getDisplayMessage();
            }
            return d_Message;
        }
    }
}