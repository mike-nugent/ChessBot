package ui;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import exceptions.ChessException;
import pieces.Piece;
import values.Board;
import values.ExtendedJPanel;
import values.Square;

public class PieceUI extends ExtendedJPanel  implements MouseListener
{
    private BoardUI _board;
    private JLabel _pieceImage;
    private String _imageName;
    List<JLabel> tmpGlows = new ArrayList<JLabel>();
    private static List<JLabel> lastMoves = new ArrayList<JLabel>();
    private Piece _piece;
    
    public PieceUI(Piece piece, BoardUI board)
    {
    	_piece = piece;
        _board = board;
        ImageIcon image = null; 
        _imageName = _piece.getColor().name() + _piece.getPieceInfo().name();
        try
        {
         image = new ImageIcon(
				ImageIO.read(
						new File("src/main/resources/"+_imageName+".png")).getScaledInstance(-1, 60, Image.SCALE_SMOOTH));
		
        } catch (Exception e)
        {
        	System.out.println("error in " + this);
        	e.printStackTrace();
        }
        
        _pieceImage = new JLabel(image); 
        _pieceImage.setLayout(null);
        this.setLayout(null);
        _pieceImage.setBounds(0, 0, image.getIconWidth(), image.getIconHeight() );
        this.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		this.add(_pieceImage);
		this.setOpaque(false);
		_pieceImage.setOpaque(false);
        
		
		//set up the mouse listening.
		this.addMouseListener(this); 
		
		
		 Runnable r = new Runnable() {
		      public void run() {
		        try {
		          erf();
		        } catch (Exception x) {
		          // in case ANY exception slips through
		          x.printStackTrace();
		        }
		      }
		    };

		    Thread internalThread = new Thread(r);
		    internalThread.start();
		 
		
    }
    
    public String getImageName()
    {
    	return _imageName;
    }


    
    //----- MOUSE EVENTS -----------
    
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e) 
    {
	}

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    private boolean isDragging = false;
    public void mousePressed(MouseEvent e) 
    {
    	System.out.println("Detected mouse press on " + this); 
    	isDragging = true;
    	_board.setComponentZOrder(this, 0);
    	List<Square> getNextSres = _piece.getNextLegalMoves();
    	
    	tmpGlows = new ArrayList<JLabel>(); 
    	for(Square s : getNextSres)
    	{    		
    		if(s != null)
    		{
        		JLabel newglow = makeGlow();
        		newglow.setLocation(s.getCornerX(), s.getCornerY());
        		tmpGlows.add(newglow);
    		}
    		else
    		{
    			System.out.println("Error, found null in " + getNextSres); 
    		}
    	}	
	}

    private JLabel makeGlow()
    {
    	ImageIcon glow = new ImageIcon("src/main/resources/chessglow.gif");
    	JLabel glowLabel = new JLabel(glow);
    	
		glowLabel.setBounds(0,0,glow.getIconWidth(),glow.getIconHeight());
		_board.add(glowLabel, this.getComponentCount());		
		glowLabel.setVisible(true);
		return glowLabel;
    }
    
    
    private void showLastMoves(Square fromSquare, Square toSquare)
    {
    	
    	if(!lastMoves.isEmpty())
    	{
    		for(JLabel move : lastMoves)
    		{
    			_board.remove(move);
    		}
    		
    		lastMoves.clear();
    	}
    	
    	ImageIcon lastMove1 = new ImageIcon("src/main/resources/lastmove.gif");
    	ImageIcon lastMove2 = new ImageIcon("src/main/resources/lastmove.gif");

    	JLabel lastM1 = new JLabel(lastMove1);
    	JLabel lastM2 = new JLabel(lastMove2);

    	lastM1.setBounds(0,0,lastMove1.getIconWidth(),lastMove1.getIconHeight());
    	lastM2.setBounds(0,0,lastMove2.getIconWidth(),lastMove2.getIconHeight());

		_board.add(lastM1, this.getComponentCount()-1);	
		_board.add(lastM2, this.getComponentCount()-1);		
		
		lastM1.setLocation(fromSquare.getCornerX(), fromSquare.getCornerY());
		lastM2.setLocation(toSquare.getCornerX(), toSquare.getCornerY());

		lastMoves.add(lastM1);
		lastMoves.add(lastM2);
    }
 


	private void erf() 
    {
		try
		{  
	    	while(true)
	    	{ 
	    		if(isDragging)
	    		{
		    		Point p = MouseInfo.getPointerInfo().getLocation();
		    		double dx = p.x - _board.getLocationOnScreen().getX();
		    		double dy = p.y - _board.getLocationOnScreen().getY();
		    		
		    		this.setX( (int) (dx - getWidth() /2));
		    		this.setY( (int) (dy - getHeight() /2));
	    		}
	    	

	    		Thread.sleep(16); 
	    	}
		} catch (Exception e)
		{ 
			e.printStackTrace();
		}
	}

	/**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e) 
    {
    	System.out.println("Detected mouse release on " + this);

    	isDragging = false;
    	Square newSquare = _board.getNearestSquare(this.getX() + this.getWidth() /2, this.getY() + this.getHeight() /2);
    	String lastMove = null;
    	try 
    	{ 
			lastMove = moveTo(newSquare);
		} 
    	catch (ChessException e1) 
    	{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	finally
    	{
	    	for(JLabel glow : tmpGlows)
	    	{
	    		_board.remove(glow);
	    	}
	    	tmpGlows = new ArrayList<JLabel>();
	    	_board.updateEverything(lastMove);

    	}
	}
    
    public String moveTo(final Square newSquare)
            throws ChessException
    {
    	Board b = _piece.getBoard();
    	
    	if(b.canMoveTo(_piece, newSquare))
    	{
    		Square fromSquare = _piece.getCurrentSquare();
    		Square toSquare = newSquare;
    		String hasMoved = b.move(_piece, newSquare);
    		if( hasMoved == null)
    		{
    			//The move did not happen.
    			return null;
    		}
    		else
    		{
        		showLastMoves(fromSquare, toSquare);
        		positionOnSquare(toSquare);	
        		return hasMoved;
    		}
    	}
    	else
    	{ 
    		//Set the piece back to where it was.
    		System.out.println("Putting " + this + " back on " + _piece.getCurrentSquare());
    		positionOnSquare(_piece.getCurrentSquare());
    		return null;
    	}
    }
     
    

    /**
     * Invoked when the mouse enters a component. 
     */
    public void mouseEntered(MouseEvent e) 
    {
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e) 
    {
    	this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
    
	
	public void setX(int X) 
	{
		this.setLocation(X , getY());
	}
	 
	
	public void setY(int Y)
	{
		this.setLocation(getX(), Y );
	}
	
	public Piece getPiece()
	{
		return _piece;
	}
	

	public void positionOnSquare(Square square) 
	{
		this.setX(square.getX() - this.getWidth()/2);
		this.setY(square.getY() - this.getHeight()/2);		
	}
	
}
