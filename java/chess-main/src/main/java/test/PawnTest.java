package test;

import org.junit.Assert;
import org.junit.Test;

import exceptions.ChessException;
import exceptions.IllegalMoveException;
import pieces.Pawn;
import ui.BoardUI;
import values.Board;
import values.Color;
import values.Square;

public class PawnTest extends ChessTest
{

    @Test(expected = IllegalMoveException.class)
    public void testEnemyPawnInFront()
            throws InstantiationException, IllegalAccessException, ChessException
    {
        final Board b = new Board(Board.NEW_GAME);
        final Pawn wPawn = new Pawn(Color.W);
        final Pawn bPawn = new Pawn(Color.B);

        b.registerPosition(wPawn, Square.E4);
        b.registerPosition(bPawn, Square.E5);
        b.setTurn(Color.W);

       // wPawn.moveTo(Square.E5);
    }

    @Test(expected = IllegalMoveException.class)
    public void testFriendlyPawnInFront()
            throws InstantiationException, IllegalAccessException, ChessException
    {
        final Board b = new Board(Board.NEW_GAME);
        final Pawn wPawn = new Pawn(Color.W);
        final Pawn w2Pawn = new Pawn(Color.W);

        b.registerPosition(wPawn, Square.E4);
        b.registerPosition(w2Pawn, Square.E5);
        b.setTurn(Color.W);

       // wPawn.moveTo(Square.E5);
    }

    @Test(expected = IllegalMoveException.class)
    public void testOpeningMoveException()
            throws InstantiationException, IllegalAccessException, ChessException
    {
        final Board b = new Board(Board.NEW_GAME);
        final Pawn wPawn = new Pawn(Color.W);
        final Pawn w2Pawn = new Pawn(Color.W);

        b.registerPosition(wPawn, Square.E2);
        b.registerPosition(w2Pawn, Square.E3);
        b.setTurn(Color.W);

      //  wPawn.moveTo(Square.E4);
    }

    @Test
    public void testOpeningMoveLegal()
            throws InstantiationException, IllegalAccessException, ChessException
    {
        final Board b = new Board(Board.NEW_GAME);
        final Pawn pwn = new Pawn(Color.W);

        b.registerPosition(pwn, Square.E2);
        b.setTurn(Color.W);

        Assert.assertEquals(pwn, b.getPiece(Square.E2));
        Assert.assertEquals(null, b.getPiece(Square.E4));
       // pwn.moveTo(Square.E4);
        Assert.assertEquals(null, b.getPiece(Square.E2));
        Assert.assertEquals(pwn, b.getPiece(Square.E4));
    }
}
