package values;

import static values.Color.B;
import static values.Color.W;

import java.util.LinkedList;
import java.util.List;

public enum Square
{
    A8 (8, 1, W), B8 (8, 2, B), C8 (8, 3, W), D8 (8, 4, B), E8 (8, 5, W), F8 (8, 6, B), G8 (8, 7, W), H8 (8, 8, B),
    A7 (7, 1, B), B7 (7, 2, W), C7 (7, 3, B), D7 (7, 4, W), E7 (7, 5, B), F7 (7, 6, W), G7 (7, 7, B), H7 (7, 8, W),
    A6 (6, 1, W), B6 (6, 2, B), C6 (6, 3, W), D6 (6, 4, B), E6 (6, 5, W), F6 (6, 6, B), G6 (6, 7, W), H6 (6, 8, B),
    A5 (5, 1, B), B5 (5, 2, W), C5 (5, 3, B), D5 (5, 4, W), E5 (5, 5, B), F5 (5, 6, W), G5 (5, 7, B), H5 (5, 8, W),
    A4 (4, 1, W), B4 (4, 2, B), C4 (4, 3, W), D4 (4, 4, B), E4 (4, 5, W), F4 (4, 6, B), G4 (4, 7, W), H4 (4, 8, B),
    A3 (3, 1, B), B3 (3, 2, W), C3 (3, 3, B), D3 (3, 4, W), E3 (3, 5, B), F3 (3, 6, W), G3 (3, 7, B), H3 (3, 8, W),
    A2 (2, 1, W), B2 (2, 2, B), C2 (2, 3, W), D2 (2, 4, B), E2 (2, 5, W), F2 (2, 6, B), G2 (2, 7, W), H2 (2, 8, B),
    A1 (1, 1, B), B1 (1, 2, W), C1 (1, 3, B), D1 (1, 4, W), E1 (1, 5, B), F1 (1, 6, W), G1 (1, 7, B), H1 (1, 8, W);

    private int _row;
    private int _col;
    private Color _color;

    private Square(final int row, final int col, final Color clr)
    {
        _row = row;
        _col = col;
        _color = clr;
    }

    public int getRow()
    {
        return _row;
    }

    public int getCol()
    {
        return _col;
    }

    public Color getColor()
    {
        return _color;
    }

    public static List<Square> getAllBlackSquares()
    {
        return getAll(B);
    }

    public static List<Square> getAllWhiteSquares()
    {
        return getAll(W);
    }

    private static List<Square> getAll(final Color clr)
    {
        final List<Square> sqrs = new LinkedList<Square>();

        for (final Square sqr : values())
        {
            if (sqr.getColor() == clr)
            {
                sqrs.add(sqr);
            }
        }
        return sqrs;
    }

    public static Square toEnum(final String square)
    {
        try
        {
            return valueOf(square.toUpperCase());
        }
        catch (final Exception e)
        {
            System.out.println("No enum for [" + square + "] : " + e);
            return null;
        }
    }

    public static Square get(final int r, final int c)
    {
        if (r < 1 || r > 8 || c < 1 || c > 8)
        {
            return null;
        }

        for (final Square sqr : values())
        {
            if (sqr.getRow() == r && sqr.getCol() == c)
            {
                return sqr;
            }
        }
        return null;
    }

	public int getX()
	{
    	int col = _col;
    	double spacing = 70.5; 
    	return  (int) (55 + ((col-1) * spacing)); 
	}

	public int getY() 
	{
    	int row = _row;
    	double spacing = 70.5; 
		return (int) (55 + ((9-row-1) * spacing));
	}
	
	public int getCornerX()
	{
    	int col = _col;
    	double spacing = 70.5; 
    	return  (int) (16 + ((col-1) * spacing)); 
	}

	public int getCornerY() 
	{
    	int row = _row;
    	double spacing = 70.5; 
		return (int) (16 + ((9-row-1) * spacing));
	}
}
