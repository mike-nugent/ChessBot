package pieces;

import java.util.ArrayList;
import java.util.List;

import ui.BoardUI;
import values.Board;
import values.Color;
import values.PieceInfo;
import values.Square;
 
public class Knight extends Piece
{
 
    public Knight(Color color)
    {
        super(PieceInfo.N, color);
    }

    public List<Square> getSquares(final Square sqr, final Board brd)
    { 
        final int row = sqr.getRow();
        final int col = sqr.getCol();
        
        List<Square> squares  = new ArrayList<Square>();

        squares.add(Square.get(row + 2, col - 1));
        squares.add(Square.get(row + 2, col + 1));
        squares.add(Square.get(row + 1, col + 2));
        squares.add(Square.get(row + 1, col - 2));
        squares.add(Square.get(row - 1, col + 2));
        squares.add(Square.get(row - 1, col - 2));
        squares.add(Square.get(row - 2, col - 1));
        squares.add(Square.get(row - 2, col + 1));
        
        squares = filter(squares);

        return brd.getAvailableSquares(this, squares);

    }
}
