package pieces;

import java.util.ArrayList;
import java.util.List;

import exceptions.ChessException;
import ui.BoardUI;
import values.Board;
import values.Color;
import values.PieceInfo;
import values.Square;

public class Pawn extends Piece
{
    private boolean _isEmCapturable;


    public Pawn(final Color color)
    {
    	super(PieceInfo.P, color);
        _isEmCapturable = false;
    }

    public List<Square> getSquares(final Square sqr, final Board brd)
    {
        List<Square> nextMoves = new ArrayList<Square>();

        //Custom check if the sqr is null.  If so, was captured by em-passant last move and is waiting to be discarded.
        if(sqr == null)
        {
        	return nextMoves;
        }
        
        final int direction = (this.isWhite()) ? 1 : -1;
        final int row = sqr.getRow();
        final int col = sqr.getCol();

        //standard move;
        final Square normalSquare = Square.get(row + direction, col);
        boolean normalMoveOk = false;
        if (!brd.isAnyPieceOn(normalSquare))
        {
            normalMoveOk = true;
            nextMoves.add(normalSquare);
        }

        //two move opening
        if (!this.hasMoved())
        {
            final Square twoMoveSquare = Square.get(row + (2 * direction), col);
            if (!brd.isAnyPieceOn(twoMoveSquare) && normalMoveOk)
            {
                nextMoves.add(twoMoveSquare);
            }
        }

        //check capture moves 
        final Square rightCapture = Square.get(row + direction, col + 1);
        final Square leftCapture = Square.get(row + direction, col - 1);

        if (brd.isEnemyPieceOn(rightCapture, this))
        {
            nextMoves.add(rightCapture); 
        }

        if (brd.isEnemyPieceOn(leftCapture, this))
        {
            nextMoves.add(leftCapture);
        }

        //check em-passant
        final Square rightEmCapture = Square.get(row, col + 1);
        final Square leftEmCapture = Square.get(row, col - 1);

        if (brd.isEmCapturablePieceOn(rightEmCapture, this))
        {
            nextMoves.add(Square.get(row + direction, col + 1));
        }

        if (brd.isEmCapturablePieceOn(leftEmCapture, this))
        {
            nextMoves.add(Square.get(row + direction, col - 1));
        }
        
        nextMoves = filter(nextMoves);


        return nextMoves;
    }
}
