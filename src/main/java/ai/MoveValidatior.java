package ai;

import java.util.List;
import java.util.Map;

import exceptions.ChessException;
import exceptions.IllegalMoveException;
import exceptions.UnknownMoveException;
import pieces.IPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import values.Board;
import values.Square;

public class MoveValidatior
{
    public void validate(
            final Board board,
            final String move,
            final boolean whiteToMove)
            throws ChessException
    {
        try
        {
            //Nf3
            //exf4
            //Rxf1
            //e3
            //0-0
            //0-0-0
            //h8#Q
            final String token = move.trim();

            System.out.println("analyzing token: " + token);
            if (token.contains("-") || token.contains("#"))
            {
                handleSpecial(token);
            }

        }
        catch (final Exception e)
        {
            e.printStackTrace();
            throw new UnknownMoveException("Cannot do move [" + move + "] : " + e);
        }
    }

    private void handleSpecial(final String move)
    {
        System.out.println("TODO - handle: " + move);
    }

    public void validate(
            final Board board,
            final Piece pieceToMove,
            final Square newSquare)
            throws IllegalMoveException
    {
    	Square currentSquare = pieceToMove.getCurrentSquare();
        System.out.println(pieceToMove + " : " + currentSquare + " : " + newSquare);

        //its your turn
        System.out.println("Checking turn...");
        if (!board.getCurrentTurn().equals(pieceToMove.getColor()))
        {
            throw new IllegalMoveException("Dude, it's not your turn");
        }

        System.out.println("Checking if move is legal for piece type...");
        List<Square> possibleLocations = pieceToMove.getSquares(currentSquare, board); 
        if(!possibleLocations.contains(newSquare))
        {
            throw new IllegalMoveException(pieceToMove + " cannot move to " + newSquare);
        }

        //cannot capture self
        System.out.println("Checking to capture self...");
        if (board.isFriendlyPieceOn(newSquare, pieceToMove))
        {
            throw new IllegalMoveException("Cannot capture friendly pieces");
        }

        //pawn cannot capture directly in front him
        System.out.println("Checking pawn jump...");
        if (pieceToMove instanceof Pawn &&
                currentSquare.getCol() == newSquare.getCol() &&
                board.isEnemyPieceOn(newSquare, pieceToMove))
        {
            throw new IllegalMoveException("Pawns cannot capture pieces on same column");
        }

        //validate first jump move for pawn
        System.out.println("Checking first jump move for pawn...");
        if (pieceToMove instanceof Pawn &&
                Math.abs(currentSquare.getRow() - newSquare.getRow()) == 2)
        {
            int direction = 1;
            if (pieceToMove.isBlack())
            {
                direction = -1;
            }

            final Square jumpedSqr = Square.get(currentSquare.getRow() + direction, currentSquare.getCol());
            if (board.isAnyPieceOn(jumpedSqr))
            {
                throw new IllegalMoveException("Pawns cannot jump pieces");
            }
        }
        
        //Check if moving the piece puts the king in check.
        System.out.println("Checking if the friendly king will be in check after the move..."); 
        if(board.movePutsKingInCheck(pieceToMove, pieceToMove.getColor(), pieceToMove.getCurrentSquare(), newSquare))
        {
        	throw new IllegalMoveException("Moving this piece would leave your king in check.  It cannot be moved..");
        }

    }

    public void validate(final String token)
    {
        System.out.println("TODO - validate token [" + token + "]");

    }
}
