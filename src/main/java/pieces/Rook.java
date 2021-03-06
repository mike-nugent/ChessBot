package pieces;

import java.util.ArrayList;
import java.util.List;

import ui.BoardUI;
import values.Board;
import values.Color;
import values.PieceInfo;
import values.Square;

public class Rook extends Piece
{

    public Rook(final Color color)
    {
        super(PieceInfo.R, color);
    }

    public List<Square> getSquares(final Square sqr, final Board brd)
    {
        List<Square> sqrs = new ArrayList<Square>();
        sqrs.addAll(brd.getLine(this, sqr, 1, 0));
        sqrs.addAll(brd.getLine(this, sqr, 0, 1)); 
        sqrs.addAll(brd.getLine(this, sqr, -1, 0));
        sqrs.addAll(brd.getLine(this, sqr, 0, -1));
        
        sqrs = filter(sqrs);
        return brd.getAvailableSquares(this, sqrs);

    }
}
