package values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.MoveTokenGenerator;
import ai.MoveValidatior;
import exceptions.ChessException;
import pieces.IPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import sets.black.BlackBishop;
import sets.black.BlackKing;
import sets.black.BlackKnight;
import sets.black.BlackPawn;
import sets.black.BlackQueen;
import sets.black.BlackRook;
import sets.white.WhiteBishop;
import sets.white.WhiteKing;
import sets.white.WhiteKnight;
import sets.white.WhitePawn;
import sets.white.WhiteQueen;
import sets.white.WhiteRook;
import ui.BoardUI;

public class Board
{
    private final Map<Square, Piece> _map;

    private Color _currentTurn;
    private final MoveValidatior _moveValidatior;
    private final MoveTokenGenerator _moveTokenGenerator;
    private King whiteKing, blackKing;
 
	private int _turnCount = 1;

	private Pawn _emCaptureablePawn;
	
	public static final String NEW_GAME = "SetupNewGame";
	
	
	public Board()
	{
        _currentTurn = null;
        _map = new HashMap<Square, Piece>();
        _moveValidatior = new MoveValidatior();
        _moveTokenGenerator = new MoveTokenGenerator();
	}
 
    public Board(String position)
    {
    	this();
        if(position.equals(NEW_GAME)) 
        {
        	setUpNewGame();
        }
        else
        {
        	setupPosition(position);
        }
	}
    

	private void setupPosition(String position) 
	{
		System.out.println("TODO - setup " + position);
	} 


	public void setUpNewGame() 
	{
		//setup black pieces
		setPiece(Square.A8, new BlackRook());
		setPiece(Square.B8, new BlackKnight());
		setPiece(Square.C8, new BlackBishop());
		setPiece(Square.D8, new BlackQueen());
		setPiece(Square.E8, new BlackKing());
		setPiece(Square.F8, new BlackBishop());
		setPiece(Square.G8, new BlackKnight());
		setPiece(Square.H8, new BlackRook());
		setPiece(Square.A7, new BlackPawn());
		setPiece(Square.B7, new BlackPawn());
		setPiece(Square.C7, new BlackPawn());
		setPiece(Square.D7, new BlackPawn());
		setPiece(Square.E7, new BlackPawn());
		setPiece(Square.F7, new BlackPawn());
		setPiece(Square.G7, new BlackPawn());
		setPiece(Square.H7, new BlackPawn());
		
		
		setPiece(Square.A1, new WhiteRook());
		setPiece(Square.B1, new WhiteKnight());
		setPiece(Square.C1, new WhiteBishop());
		setPiece(Square.D1, new WhiteQueen());
		setPiece(Square.E1, new WhiteKing());
		setPiece(Square.F1, new WhiteBishop());
		setPiece(Square.G1, new WhiteKnight());
		setPiece(Square.H1, new WhiteRook());
		setPiece(Square.A2, new WhitePawn());
		setPiece(Square.B2, new WhitePawn());
		setPiece(Square.C2, new WhitePawn());
		setPiece(Square.D2, new WhitePawn());
		setPiece(Square.E2, new WhitePawn());
		setPiece(Square.F2, new WhitePawn());
		setPiece(Square.G2, new WhitePawn());
		setPiece(Square.H2, new WhitePawn());
		setTurn(Color.W);
		
	}
	
	public Map<Square, Piece> getMap()
	{
		return _map;
	}
	
	public void setEmCaptureablePawn(Pawn pwn)
	{
		_emCaptureablePawn = pwn;
	}
	
	public Pawn getEmCapturablePawn()
	{
		return _emCaptureablePawn;
	}
    
    public void setPiece(Square square, Piece piece)
    {
        piece.setBoard(this);
		registerPosition(piece, square);
    }
    
    public Color getCurrentTurn()
    {
        return _currentTurn;
    }

    public boolean isAnyPieceOn(final Square sqr)
    {
        return !isEmpty(sqr);
    }

    public boolean isEmpty(final Square sqr)
    {
        final IPiece pce = _map.get(sqr);
        return pce == null;
    }

    public boolean isEnemyPieceOn(final Square sqr, final IPiece pce)
    {
        final Color clr = pce.getColor();
        final IPiece enemyPiece = _map.get(sqr);
        if (enemyPiece != null)
        {
            return !enemyPiece.getColor().equals(clr);
        }
        else
        {
            return false;
        }
    }

    public boolean isFriendlyPieceOn(final Square sqr, final IPiece pce)
    {
        final Color clr = pce.getColor();
        final IPiece friendlyPiece = _map.get(sqr);
        if (friendlyPiece != null)
        {
            return friendlyPiece.getColor().equals(clr);
        }
        else
        {
            return false;
        }
    }

    public boolean isEmCapturablePieceOn(final Square sqr, final Pawn pwn)
    {
        final Color clr = pwn.getColor();
        final IPiece enemyPiece = _map.get(sqr);
        if (enemyPiece != null && enemyPiece instanceof Pawn)
        {
            final Pawn enemyPawn = (Pawn) enemyPiece;
            if (enemyPawn.getColor() != clr && _emCaptureablePawn == enemyPawn)
            {
                return true;
            }
        }

        return false;
    }
    
    public Board cloneBoard()
    {
    	Board b = new Board();
    	b.setTurn(getCurrentTurn());
    	b.setEmCaptureablePawn(getEmCapturablePawn());
    	b.setTurnCount(getTurnCount());
    	
    	for(Piece p : _map.values())
    	{
    		b.setPiece(p.getCurrentSquare(), p.clonePiece());
    	} 
    	return b;
    }

    public List<Square> getAvailableSquares(
            final Piece p,
            final List<Square> squares)
    {
        final List<Square> ret = new ArrayList<Square>();
        for (final Square sqr : squares)
        {
        	if(sqr != null)
        	{
                if (isEmpty(sqr) || isEnemyPieceOn(sqr, p))
                {
                     ret.add(sqr);
                }	
        	}
        }
        return ret;
    }
 


    public List<Square> getLine(final IPiece pce, final Square startingSquare, final int rowInc, final int colInc)
    {
        final List<Square> sqrs = new ArrayList<Square>();
        final int row = startingSquare.getRow();
        final int col = startingSquare.getCol();

        int R = 0;
        int C = 0;

        for (int i = 0; i < 8; i++)
        {
            R += rowInc;
            C += colInc;
            final Square s = Square.get(row + R, col + C);
            if (isEmpty(s))
            {
                sqrs.add(s);
            }
            else if (isEnemyPieceOn(s, pce))
            {
                sqrs.add(s);
                return sqrs;
            }
            else
            {
                return sqrs;
            }
        }
        return sqrs;
    }

    public void setTurn(final Color side)
    {
        _currentTurn = side;
    }

    public void registerPosition(final Piece piece, final Square square)
    {
		piece.setCurrentSquare(square); 
    	if(piece instanceof King)
    	{
    		if(piece.getColor().equals(Color.B))
    			blackKing = (King) piece;
    		else
    			whiteKing = (King) piece;
    	}
        _map.put(square, piece);
    }


	public Piece getPiece(final Square sqr)
    {
        return _map.get(sqr);
    }
    

    /**
     * Returns null when the move did not happen.  Returns the move notation as a string when the move did happen.
     */
    public String move(
    		final Piece pce, 
    		final Square newSquare)
            throws ChessException
    {
    	final Square currentSquare = pce.getCurrentSquare();
        final String moveToken = _moveTokenGenerator.generateMoveToken(this, pce, currentSquare, newSquare);
        System.out.println("Token generated: " + moveToken); 


        if(newSquare.equals(currentSquare))
        {
        	//the piece was picked up and put back in it's original spot.
        	return null;
        }

        //Otherwise, validate the move.
        final Piece toSquare = _map.remove(newSquare);
        final Piece pieceToMove = _map.remove(currentSquare);
        

        //A piece was captured!
        if(toSquare != null)
        {
        	toSquare.setCurrentSquare(null); 
        }
     
        //Check for Em-passant move.  This flags the pawn moving 2 spaces as being em capturable.
        boolean skipFlag = false;
        if(pieceToMove instanceof Pawn)
        {
        	
        	//If moving two forward, set the pawn to be attackable by en-passant.
        	if(!pieceToMove.hasMoved() && Math.abs(currentSquare.getRow() - newSquare.getRow()) == 2)
        	{
        		skipFlag = true;
        		_emCaptureablePawn = (Pawn) pieceToMove; 
        	}
        	else
        	{
        		if(Math.abs(currentSquare.getRow() - newSquare.getRow()) == 1 &&
        				Math.abs(currentSquare.getCol() - newSquare.getCol()) == 1)
        		{
        			if(_emCaptureablePawn != null)
        			{
            			_map.remove(_emCaptureablePawn);
            			_emCaptureablePawn.setCurrentSquare(null); 		
        			}
        		}
        	}
        }
        
        if(!skipFlag)
        {
        	_emCaptureablePawn = null;
        }

		registerPosition(pieceToMove, newSquare);
		
		//Need to check here for castling, or pawn promotion
		//TODO - check for pawn promotion
		if(pce instanceof King)
		{
			if(!pce.hasMoved())
			{
				if(pce.isWhite())
				{
					if(newSquare.equals(Square.G1))
					{
						//white king side castle
						WhiteRook H1 = (WhiteRook) _map.remove(Square.H1);
						registerPosition(H1, Square.F1 );
					} 
					else if (newSquare.equals(Square.C1)) 
					{
						//white queen side castle
						WhiteRook A1 = (WhiteRook) _map.remove(Square.A1);
						registerPosition(A1, Square.D1 );
					}
				}
				else
				{
					if(newSquare.equals(Square.G8))
					{
						//black king side castle
						BlackRook H8 = (BlackRook) _map.remove(Square.H8);
						registerPosition(H8, Square.F8 );
					} 
					else if (newSquare.equals(Square.C8))
					{
						//black queen side castle
						BlackRook A8 = (BlackRook) _map.remove(Square.A8);
						registerPosition(A8, Square.D8 );
					}
				}
			}
		}
		
		if(_currentTurn.equals(Color.W))
			setTurn(Color.B);
		else
			setTurn(Color.W);
		
		if(_currentTurn.equals(Color.W)) 
			_turnCount++;
		
		pce.hasMoved(true);
		
        return moveToken;

    }

    /*
     * public void move(final String moveToken)
     * throws ChsExc
     * {
     * //TODO - better validation via just notation.
     * _moveValidatior.validate(moveToken, _currentTurn);
     * }
     */

    public boolean anyOtherSimilarPieceCanMoveTo(final Piece pce, final Square currentSquare, final Square newSquare)
    {
        //loop through the entire board getting each piece, keeping the
        //same type and color as piece. (excluding piece).

        //Then check if any of those pieces can move to the square.  

        for (final Square s : Square.values())
        {
            if (s != currentSquare)
            {
                final Piece p = this.getPiece(s);
                if (p != null && p.getCode().equals(pce.getCode()) && p.getColor().equals(pce.getColor()))
                {
                    if (canMoveTo(p,newSquare))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean canMoveTo(final Piece pce, final Square newSquare)
    {
    	try
    	{
    		_moveValidatior.validate(this, pce, newSquare);
        	System.out.println("No issues, returning true!");

    		return true;
    	} catch (Exception e)
    	{
    		System.out.println("Illegal move detected. ["+e.getMessage()+"] Resetting piece..." );
    		return false; 
    	}
    }



	public boolean movePutsKingInCheck(
			final Piece p,
			final Color c,
			final Square fromSquare,
			final Square toSquare) 
	{
		//do the move with piece.  check other sides king
		
		Board clone = cloneBoard();
		
        
       King myKing = clone.getKing(c);
       Map<Square, Piece> map = clone.getMap();
       
       Piece tmpA = map.remove(fromSquare);
       Piece tmpB = map.remove(toSquare);
       map.put(toSquare, tmpA);
       if(tmpA != null)
    	   tmpA.setCurrentSquare(toSquare);
       if(tmpB != null)
        	tmpB.setCurrentSquare(null);
        
        final boolean result = isKingInCheck(clone, myKing);
       
       return result;
	}

	public int getTurnCount()
	{
		return _turnCount;
	}
	public void setTurnCount(int count)
	{
		_turnCount = count;
	}
 
	public boolean isKingInCheck(Board clone, final King king)
	{	
		List<Piece> pieces = clone.getPieces(king.getOppositeColor());
		
		for(Piece p : pieces)
		{ 
			//Ignore the kings, they can never put a king in check
			if(!(p instanceof King)) 
			{
				System.out.println("Checking if " + p + " is putting king in check");
				if(p.getSquares(p.getCurrentSquare(), clone).contains(king.getCurrentSquare()))
				{
					System.out.println("	... YES it does!");
					return true;
				}
				else
				{
					System.out.println("	... nope");
				}
			}
		}
		return false;
	}

	public King getKing(final Color color)
	{
		if(color.equals(Color.B))
		{
			return blackKing;
		}
		else
		{
			return whiteKing;
		}
	}

	public List<Piece> getPieces(Color oppositeColor) 
	{
		List<Piece> pieces = new ArrayList<Piece>();
		Collection<Piece> mapPieces = _map.values();
		for (Piece p : mapPieces)
		{
			if(p.getColor().equals(oppositeColor))
				pieces.add(p);
		}
		return pieces;
	}

	public boolean noEnemyPieceCanMoveTo(Square s, Color c)
	{
		List<Piece> pieces = getPieces(c);
		for(Piece p : pieces)
		{
			if(p.getSquares(p.getCurrentSquare(), this).contains(s))
			{
				return false;
			}
		}
		
		return true;
	}


}
