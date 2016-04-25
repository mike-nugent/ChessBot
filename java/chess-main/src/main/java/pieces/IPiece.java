package pieces;

import java.util.List;

import ui.BoardUI;
import values.Board;
import values.Color;
import values.Square;
import exceptions.ChessException;

public interface IPiece
{
    public Square getCurrentSquare();

    public double getValue();

    public Color getColor();

    public boolean hasMoved();

    public boolean isBlack();

    public boolean isWhite();

    public List<Square> getSquares(Square sqr, final Board brd);

    
    public void setCurrentSquare(Square square);

    public String getCode();
    

	public Color getOppositeColor();

}
