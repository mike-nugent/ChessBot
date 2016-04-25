package exceptions;

@SuppressWarnings("serial")
public class IllegalMoveException extends ChessException
{

    public IllegalMoveException(final String msg)
    {
        super(msg);
    }

}
