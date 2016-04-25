package exceptions;

@SuppressWarnings("serial")
public class UnknownMoveException extends ChessException
{
    public UnknownMoveException(final String msg)
    {
        super(msg);
    }
}
