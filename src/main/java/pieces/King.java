package pieces;

import java.util.ArrayList;
import java.util.List;

import ui.BoardUI;
import values.Board;
import values.Color;
import values.PieceInfo;
import values.Square;

public class King extends Piece
{

    public King(Color color)
    {
        super(PieceInfo.K, color);
    }

    public List<Square> getSquares(final Square sqr, final Board brd)
    {
        final int row = sqr.getRow();
        final int col = sqr.getCol();
        List<Square> squares  = new ArrayList<Square>();

        squares.add(Square.get(row + 1, col - 1));
        squares.add(Square.get(row + 1, col));
        squares.add(Square.get(row + 1, col + 1));
        squares.add(Square.get(row, col + 1));
        squares.add(Square.get(row - 1, col + 1));
        squares.add(Square.get(row - 1, col));
        squares.add(Square.get(row - 1, col - 1));
        squares.add(Square.get(row, col - 1));
        
        squares.addAll(checkCastling(sqr, brd));
        squares = filter(squares);

       

        return brd.getAvailableSquares(this, squares);
    }

     
    public List<Square> checkCastling(final Square sqr, final Board brd)
    { 
    	List<Square> castles = new ArrayList<Square>();
    	 if(!hasMoved())
         {
         	//Castling is still available, under the following conditions:
         	//1) King hasnt moved
         	//2) The rook hasnt moved
         	//3) The king is not in check
         	//4) The king cannot castle "through" check
         	
         	//TODO - is king in check?
    		 if(getBoard().isKingInCheck(getBoard(), this))
    		 {
    			 return castles;
    		 }
         	
         	//Check to see if the rooks on either side have moved
    		 
  			Board b = getBoard();
         	if(isWhite())
         	{
         		//First check king side castle
         		Piece kingRook = brd.getPiece(Square.H1);
         		if(kingRook != null && !kingRook.hasMoved())
         		{
         			//King castling is still eligible.
             		//check the squares F1, G1 for kinkside 0-0
         			Piece F1 = brd.getPiece(Square.F1);
         			Piece G1 = brd.getPiece(Square.G1);
         			if(F1 == null && G1 == null &&
         					b.noEnemyPieceCanMoveTo(Square.F1, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.G1, getOppositeColor()))
         			{
         				castles.add(Square.G1);
         			}
         		}
         		
             	Piece queenRook = brd.getPiece(Square.A1);
             	if(queenRook != null && !queenRook.hasMoved())
             	{
         			//Queen castling is still eligible.
             		//check the B1,C1,D1 for queenside 0-0-0
         			Piece B1 = brd.getPiece(Square.B1);
         			Piece C1 = brd.getPiece(Square.C1);
         			Piece D1 = brd.getPiece(Square.D1);
         			if(B1 == null && C1 == null && D1 == null &&
         					b.noEnemyPieceCanMoveTo(Square.B1, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.C1, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.D1, getOppositeColor()))
         			{
         				castles.add(Square.C1);
         			}
             	}
         	}
         	else 
         	{
         		//First check king side castle
         		Piece kingRook = brd.getPiece(Square.H8);
         		if(kingRook != null && !kingRook.hasMoved())
         		{
         			//King castling is still eligible.
             		//For black king, check the squares F8, G8 for kingside 0-0
         			Piece F8 = brd.getPiece(Square.F8);
         			Piece G8 = brd.getPiece(Square.G8);
         			if(F8 == null && G8 == null &&
         					b.noEnemyPieceCanMoveTo(Square.F8, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.G8, getOppositeColor()))
         			{
         				castles.add(Square.G8);
         			}
         		}
         		
             	Piece queenRook = brd.getPiece(Square.A8);
             	if(queenRook != null && !queenRook.hasMoved())
             	{
         			//Queen castling is still eligible.
             		//check the squares B8,C8,D8 for queenside 0-0-0
         			Piece B8 = brd.getPiece(Square.B8);
         			Piece C8 = brd.getPiece(Square.C8);
         			Piece D8 = brd.getPiece(Square.D8);
         			if(B8 == null && C8 == null && D8 == null &&
         					b.noEnemyPieceCanMoveTo(Square.B8, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.C8, getOppositeColor()) &&
         					b.noEnemyPieceCanMoveTo(Square.D8, getOppositeColor()))
         			{
         				castles.add(Square.C8);
         			}
             	}
 			}
         }
    	 
    	 return  castles;
    }
    


}
