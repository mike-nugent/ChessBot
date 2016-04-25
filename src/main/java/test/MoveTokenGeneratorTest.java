package test;


import org.junit.Assert;
import org.junit.Test;

import ai.MoveTokenGenerator;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import sets.black.BlackKing;
import sets.black.BlackPawn;
import sets.white.WhiteKnight;
import sets.white.WhitePawn;
import values.Board;
import values.Square;

public class MoveTokenGeneratorTest extends ChessTest
{
    final MoveTokenGenerator t = new MoveTokenGenerator();
    final Board board = new Board(Board.NEW_GAME);

    @Test
    public void test_e4()
    { 
        final Pawn p = new WhitePawn();
        board.registerPosition(p, Square.E3);

        Assert.assertEquals("e4", t.generateMoveToken(board, p, Square.E3, Square.E4));
    }

    @Test
    public void test_exf4()
    {
        final Pawn w = new WhitePawn();
        final Pawn b = new BlackPawn();
        board.registerPosition(w, Square.E3);
        board.registerPosition(b, Square.F4);

        Assert.assertEquals("exf4", t.generateMoveToken(board, w, Square.E3, Square.F4));
    }

    @Test
    public void test_Nb3check()
    {
    	final Knight n = new WhiteKnight();
    	final King k = new BlackKing();
    	
    	board.registerPosition(n, Square.A1);
    	board.registerPosition(k, Square.D4);
    	
    	Assert.assertEquals("Nb3+", t.generateMoveToken(board, n, Square.A1, Square.B3));
    }
    
    
    @Test
    public void test_Nab3()
    {
    	final Knight n = new WhiteKnight();
    	final Knight n2 = new WhiteKnight();
    	
    	board.registerPosition(n, Square.A1);
    	board.registerPosition(n2, Square.C1);
    	
    	Assert.assertEquals("Nab3", t.generateMoveToken(board, n, Square.A1, Square.B3));
    }
}
