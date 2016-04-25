package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import pieces.Piece;
import sets.black.BlackKing;
import sets.white.WhiteKing;
import values.Board;
import values.CapturedPieceIcon;
import values.Color;
import values.ExtendedJPanel;
import values.Square;
import values.TurnArrow;

public class BoardUI extends ExtendedJPanel
{
	//The current position to show
	Board _currentBoard;
	
	List<PieceUI> _activePieces;
	List<PieceUI> _capturedPieces;

	//All UI components unrelated to logic.
    private final JLabel _boardImage;
    private TurnArrow _blackTurnImage;
    private TurnArrow _whiteTurnImage;
	private JPanel _capturedWhitePieces;
	private JPanel _capturedBlackPieces;
	private JTextArea _moveText;

	private JLabel _checkWarningBox; 
	
    public BoardUI()
    {
    	_activePieces = new ArrayList<PieceUI>();
    	_capturedPieces = new ArrayList<PieceUI>();
		ImageIcon chessboard = new ImageIcon("src/main/resources/chessboard.png");
        _boardImage = new JLabel(chessboard); 
        _boardImage.setLayout(null);
        this.setLayout(null);
        _boardImage.setBounds(0, 0, chessboard.getIconWidth() , chessboard.getIconHeight() );
        this.setBounds(0, 0, chessboard.getIconWidth() , chessboard.getIconHeight() );

        this.setSize(chessboard.getIconWidth(), chessboard.getIconHeight());
		this.add(_boardImage);
	}

	public void setX(int X)
	{
		this.setBounds(X, this.getY(), this.getWidth(), this.getHeight());
	}
	
	public void setY(int Y)
	{
		this.setBounds(this.getX(), Y, this.getWidth(), this.getHeight());
	}

	public void setCurrentMoveText(TurnArrow whiteTurn, TurnArrow blackTurn) 
	{
		_whiteTurnImage = whiteTurn;
		_blackTurnImage = blackTurn;
	}

	public void setCapturePieceContainers(JPanel capturedWhitePieces, JPanel capturedBlackPieces) 
	{
		_capturedWhitePieces = capturedWhitePieces;
		_capturedBlackPieces = capturedBlackPieces;
	}


	public void setNotationText(JTextArea moveText) 
	{
		_moveText = moveText;
	}

	public void setUpNewGame(Board board) 
	{
		_currentBoard = board; 
		displayBoard(board);
	}
 
	private void displayBoard(Board board) 
	{		
		Map<Square, Piece> map = _currentBoard.getMap();
		for(Square s : map.keySet())
		{
			Piece p = map.get(s);
			displayPiece(p,s);
		}
		
		setTurn(board.getCurrentTurn());
	}
	
    private void displayPiece(Piece p, Square s) 
    {
    	PieceUI displayPiece = new PieceUI(p, this);
    	_activePieces.add(displayPiece);
		this.add(displayPiece, this.getComponentCount()-1);
		
		registerPosition(displayPiece, s);
	}
    
	public void respositionAllPieces() 
	{
		PieceUI flagForRemoval = null;

		for(PieceUI p : _activePieces)
		{
			if(_capturedPieces.contains(p) || p.getPiece().getCurrentSquare() == null)
			{
				flagForRemoval = p;
			}
			registerPosition(p, p.getPiece().getCurrentSquare());
		}
		
		if(flagForRemoval != null)
		{
			System.out.println("flag for removal " + flagForRemoval );
			_activePieces.remove(flagForRemoval);
			if(flagForRemoval.getPiece().getColor().equals(Color.W))
			{
				_capturedWhitePieces.add(new CapturedPieceIcon(flagForRemoval.getImageName()));
			}
			else
			{
				_capturedBlackPieces.add(new CapturedPieceIcon(flagForRemoval.getImageName()));
			}
		}
	}
     
    
    public void registerPosition(final PieceUI p, final Square s)
    {
    	if(s == null)
    	{
    		//In this case, the piece is not on a square meaning it was captured.  So remove it.
    		_capturedPieces.add(p); 
    		this.remove(p);
    	}
    	else
    	{
    		p.positionOnSquare(s);
    	}
    } 
    
    public Square getNearestSquare(int X1, int Y1)
    {
    	double minVal = 10000000;
    	Square minSquare = null;
    	for (Square s : Square.values())
    	{
    		int X2 = s.getX();
    		int Y2 = s.getY();
    		double distance =  Math.sqrt((X1-X2)*(X1-X2) + (Y1-Y2)*(Y1-Y2));
    		if(distance < minVal)
    		{
    			minVal = distance;
    			minSquare = s;
    		}
    	}
    	return minSquare;
    }
   
	
    public void setTurn(final Color side)
    {
        Color currentTurn = side;
        if(currentTurn.equals(Color.W))
        {
        	_whiteTurnImage.setVisible(true);
        	_blackTurnImage.setVisible(false);
        }
        else
        {
        	_whiteTurnImage.setVisible(false);
        	_blackTurnImage.setVisible(true);
        }
    }
 
	public void updateEverything(String lastMove) 
	{
		
		updateMoveText(lastMove);
		
    	this.respositionAllPieces();
    	this.setTurn(_currentBoard.getCurrentTurn());
    	this.drawWarningIfInCheck();
    	this.repaint();
	}

	private void drawWarningIfInCheck() 
	{
		WhiteKing WK = (WhiteKing) _currentBoard.getKing(Color.W);
		BlackKing BK= (BlackKing) _currentBoard.getKing(Color.B);
		if(_currentBoard.isKingInCheck(_currentBoard, WK))
		{
			drawWarningOnSquare(WK.getCurrentSquare());
		}
		else if(_currentBoard.isKingInCheck(_currentBoard, BK))
		{
			drawWarningOnSquare(BK.getCurrentSquare());
		}
		else
		{
			if(_checkWarningBox != null)
			{
				this.remove(_checkWarningBox);
				_checkWarningBox = null;
			}
		}
	}

	private void drawWarningOnSquare(Square kingSquare) 
	{
    	ImageIcon lastMove1 = new ImageIcon("src/main/resources/kingcheck.gif");

    	JLabel lastM1 = new JLabel(lastMove1);

    	lastM1.setBounds(0,0,lastMove1.getIconWidth(),lastMove1.getIconHeight());

		this.add(lastM1, this.getComponentCount()-1);	
		
		lastM1.setLocation(kingSquare.getCornerX(), kingSquare.getCornerY());

		_checkWarningBox = lastM1; 
	}

	private void updateMoveText(String moveToken) 
	{
		if(moveToken != null)
		{
		    String currentText = _moveText.getText();
	        if(_currentBoard.getCurrentTurn().equals(Color.W))
	        {
	        	currentText += "\t" + moveToken + "\n";
	        }
	        else
	        {
	        	currentText += "  " + _currentBoard.getTurnCount() + ". " + (_currentBoard.getTurnCount() > 9 ? "" : " ") + moveToken;
	        }
	        _moveText.setText(currentText); 
		}
	}
}
