package ai;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import sets.black.BlackKing;
import sets.white.WhiteKing;
import values.Board;
import values.Square;

public class MoveTokenGenerator
{
    public String generateMoveToken(final Board brd, final Piece pce, final Square fromSquare, final Square toSquare)
    {
    	try
    	{ 
	        final String pieceCode = pce.getCode();
	
	        String location = "";
	        if (brd.anyOtherSimilarPieceCanMoveTo(pce, fromSquare, toSquare))
	        {
	            location = fromSquare.name().toLowerCase(); 
	        }
	
	        String capture = "";
	        final Piece capturedPiece = brd.getPiece(toSquare);
	        if (capturedPiece != null)
	        {
	            capture = "x";
	        }
	
	        if (capture.equals("x") && pce instanceof Pawn)
	        {
	            System.out.println(fromSquare.name());
	            location = fromSquare.name().substring(0, 1).toLowerCase();
	        }
	
	        final String target = toSquare.name().toLowerCase();
	
	        String promotion = "";
	        if (pce instanceof Pawn && (toSquare.getRow() == 8 || toSquare.getRow() == 1))
	        {
	            //todo - find best way to do this.
	            promotion = "=?";
	        }
	        
	        //Castling
	        if(pce instanceof King)
	        {
	        	if(pce instanceof WhiteKing && !pce.hasMoved())
	        	{
	        		if(toSquare.equals(Square.G1))
	        		{
	        			return "0-0";
	        		} 
	        		else if (toSquare.equals(Square.C1))
	        		{
	        			return "0-0-0";
	        		}
	        	}
	        	else if (pce instanceof BlackKing && !pce.hasMoved())
	        	{
	        		if(toSquare.equals(Square.G8))
	        		{
	        			return "0-0";
	        		} 
	        		else if (toSquare.equals(Square.C8))
	        		{
	        			return "0-0-0";
	        		}
	        	}
	        }
	
	        
	        String check = "";
	        if(brd.movePutsKingInCheck(pce, pce.getOppositeColor(), fromSquare, toSquare))
	        {
	        	check = "+";
	        }
	
	        return pieceCode + location + capture + target + promotion + check;
    	} 
    	catch (Exception e)
    	{
    		return "???";
    	}
    }
}
