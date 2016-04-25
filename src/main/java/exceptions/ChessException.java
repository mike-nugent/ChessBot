package exceptions;

public class ChessException extends Exception
{

	private static final long serialVersionUID = -1652791043347423511L;

	public ChessException(final String msg)
    {
        super(msg);
    }
}
