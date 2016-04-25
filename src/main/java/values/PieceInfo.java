package values;

import pieces.Bishop;
import pieces.IPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public enum PieceInfo
{
    P ("", Pawn.class, 1),
    Q ("Q", Queen.class,9),
    R ("R", Rook.class,5),
    N ("N", Knight.class,3),
    K ("K", King.class,-1),
    B ("B", Bishop.class,3);

    private Class<?> _clazz;
    private double _value;
    private String _notationChar;

    private PieceInfo(
    		final String notationChar,
    		final Class<?> clazz,
    		final double value)
    {
    	_notationChar = notationChar;
        _clazz = clazz;
        _value = value;
    }

   /* public IPiece create(final Color clr)
            throws InstantiationException, IllegalAccessException
    {
        final IPiece pce = (IPiece) _clazz.newInstance();
        pce.setClr(clr);
        return pce;
    }*/

    public String getNotationChar()
    {
    	return  _notationChar;
    }
	public double getValue() 
	{
		return _value;
	}
}
