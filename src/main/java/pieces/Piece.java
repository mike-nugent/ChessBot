package pieces;

import java.util.ArrayList;
import java.util.List;

import values.Board;
import values.Color;
import values.PieceInfo;
import values.Square;

public abstract class Piece implements IPiece
{ 
    private Square _currentSquare;
    private final Color _color;
    private final PieceInfo _info;
    private boolean _hasMovedOnce; 
    private Board _board;
    
    public Piece(final PieceInfo pieceInfo, final Color color) 
    {
        _color = color;
        _info = pieceInfo;
        _hasMovedOnce = false;
        _currentSquare = null;
    }
     
	public Color getOppositeColor()
    {
    	if(_color.equals(Color.B))
    		return Color.W;
    	else
    		return Color.B;
    }
	

    public List<Square> getNextLegalMoves()
    {
    	List<Square> allMoves = getSquares(getCurrentSquare(), getBoard());
    	List<Square> filteredMoves = filterLegalMoves(allMoves);
    	return filteredMoves;
    }

	private List<Square> filterLegalMoves(List<Square> allMoves) 
	{
		List<Square> filteredMoves = new ArrayList<Square>();
		Board b = getBoard();
		Piece p = this;
		for (Square s : allMoves)
		{
			if(!b.movePutsKingInCheck(p, p.getColor(), _currentSquare, s))
			{
				filteredMoves.add(s);
			}
		}
		
		return filteredMoves;
	}

	
	
	protected List<Square> filter(List<Square> sqrs) 
	{
		List<Square> squares = new ArrayList<Square>();
		for(Square s : sqrs)
		{
			if(s != null)
			{
				squares.add(s);
			}
		}
		return squares;
	}
	
	public List<Square> filterAvailableMoves(Piece p,  List<Square> squares)
	{
		  List<Square> legalMoves = new ArrayList<Square>();
		  for(Square s : squares)
		  {
		//	  if()
		  }
		  return legalMoves;
	}
	
	public Piece clonePiece()
	{
		try 
		{
			Piece p = this.getClass().newInstance();
			p.setCurrentSquare(getCurrentSquare());
			p.hasMoved(hasMoved());
			return p;
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setBoard(Board board)
	{
		_board = board;
	}

    public Square getCurrentSquare()
    {
        return _currentSquare;
    }
    
    public void setCurrentSquare(Square square)
    {
    	_currentSquare = square;
    }


    public double getValue()
    {
        return _info.getValue();
    }
    
    public PieceInfo getPieceInfo()
    {
    	return _info;
    }

    public Color getColor()
    {
        return _color;
    }

    public boolean hasMoved()
    {
        return _hasMovedOnce;
    }
    
    public void hasMoved(boolean hasMoved)
    {
    	_hasMovedOnce = true;
    }

    public boolean isBlack()
    {
        return _color.equals(Color.B);
    }

    public boolean isWhite()
    {
        return _color.equals(Color.W);
    }

    public String getCode()
    {
        return _info.getNotationChar();
    } 
    
    @Override 
    public String toString()
    {
    	return this.getClass().getSimpleName() + " " + getCurrentSquare();
    }

	public Board getBoard() 
	{
		return _board;
	}



}
