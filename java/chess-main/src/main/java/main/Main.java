package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ui.BoardUI;
import values.Board;
import values.TurnArrow;

public class Main
{
	private static final Color COMMAND_BACKGROUND_COLOR = new Color(83, 49, 2);
	private static final Color COMMAND_FOREGROUND_COLOR = new Color(208, 208, 0);
	/** 
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 * @throws IOException 
	 */
	private static final int STAGE_WIDTH = 900;
	private static final int STAGE_HEIGHT = 900;
	
	private static final Color BACKGROUND_COLOR = new Color(240, 212, 170);
	
	private static JFrame _frame = new JFrame("Mike Nugent's Chess Program");
	private static JPanel _stage = new JPanel(new BorderLayout());
	static TurnArrow blackTurn = new TurnArrow();
	static TurnArrow whiteTurn = new TurnArrow();
	private static JTextArea _moveText;
 
	private static void createAndShowGUI() throws IOException
	{
		// Create and set up the window.
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		_frame.setSize(900, 900);
		_frame.setMinimumSize(new Dimension(STAGE_WIDTH, STAGE_HEIGHT));
 
		//Add the stage to the frame (window).  The stage is the content area where we will add things to.
		_frame.getContentPane().add(_stage);
		
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel topPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		topPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

		centerPanel.setBackground(BACKGROUND_COLOR);
		rightPanel.setBackground(BACKGROUND_COLOR);
		bottomPanel.setBackground(BACKGROUND_COLOR);
		topPanel.setBackground(BACKGROUND_COLOR);

		centerPanel.setPreferredSize(new Dimension(620, 620));
	    centerPanel.setMinimumSize(new Dimension(620, 620));
		rightPanel.setPreferredSize(new Dimension(245, 580));
		bottomPanel.setPreferredSize(new Dimension(900, 180));
		topPanel.setPreferredSize(new Dimension(900, 0));

		_stage.add(centerPanel, BorderLayout.CENTER);
		_stage.add(rightPanel, BorderLayout.LINE_END);
		_stage.add(bottomPanel, BorderLayout.PAGE_END);
		_stage.add(topPanel, BorderLayout.PAGE_START);

		
		//Create an instance of the chessboard, add it to the stage.
		BoardUI chessboard = new BoardUI();
		centerPanel.setLayout(null);
		centerPanel.add(chessboard);
		chessboard.setX(50);
		chessboard.setY(50);
		
		//Create the turn indicators;
		centerPanel.add(blackTurn);
		blackTurn.setLocation(0, chessboard.getY() + 65 );
		centerPanel.add(whiteTurn);
		whiteTurn.setLocation(0, chessboard.getY() + 485 );
		
		//Set up the captured piece array
		JPanel capturedWhitePieces = new JPanel();
		capturedWhitePieces.setLayout(new BoxLayout(capturedWhitePieces, BoxLayout.X_AXIS));
		capturedWhitePieces.setBounds(0, 0, chessboard.getWidth(), 30);
		capturedWhitePieces.setBackground(BACKGROUND_COLOR);
		JPanel capturedBlackPieces = new JPanel();
		capturedBlackPieces.setLayout(new BoxLayout(capturedBlackPieces, BoxLayout.X_AXIS));
		capturedBlackPieces.setBounds(0, 0, chessboard.getWidth(), 30);
		capturedBlackPieces.setBackground(BACKGROUND_COLOR);

		centerPanel.add(capturedWhitePieces);
		centerPanel.add(capturedBlackPieces);
		
		capturedWhitePieces.setLocation(50, 20);
		capturedBlackPieces.setLocation(50, 650);
		chessboard.setCapturePieceContainers(capturedWhitePieces, capturedBlackPieces);
 

		
		//Create the right area.
		JPanel tmpRight = new JPanel();
		tmpRight.setBackground(BACKGROUND_COLOR);
		tmpRight.setLayout(new BoxLayout(tmpRight, BoxLayout.Y_AXIS));
		_moveText = new JTextArea();
		_moveText.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		JScrollPane scroller = new JScrollPane(_moveText);
		scroller.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		_moveText.setBackground(BACKGROUND_COLOR);
		_moveText.setForeground(Color.black);
		_moveText.setFont(new Font("Verdana", Font.BOLD, 15 )); 
		scroller.setPreferredSize(new Dimension(200, 560));
		_moveText.setLineWrap(true);  
		chessboard.setNotationText(_moveText);
		_moveText.setEditable(false); 
		
		//Set the text above the move history output
		JLabel moveHistoryText = new JLabel("Move History: ");
		moveHistoryText.setForeground(Color.BLACK);
		moveHistoryText.setFont(new Font("Verdana", Font.BOLD, 20 ));
		Component emptySpace = Box.createVerticalStrut(40);

		tmpRight.add(emptySpace);

		tmpRight.add(moveHistoryText);
		chessboard.setCurrentMoveText(whiteTurn, blackTurn);
		tmpRight.add(scroller);

		JPanel tmpBottom  = new JPanel(); 
		tmpBottom.setLayout(new BoxLayout(tmpBottom, BoxLayout.Y_AXIS));
		tmpBottom.setBackground(Color.black);
		JLabel commandLabel = new JLabel("Commands: " + listCommands());
		commandLabel.setForeground(Color.gray);
		tmpBottom.add(commandLabel);
		JTextArea commandTxt = new JTextArea();
		JScrollPane commandScroll = new JScrollPane(commandTxt);
		commandScroll.setPreferredSize(new Dimension(890, 140));
		commandScroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		commandTxt.setBackground(COMMAND_BACKGROUND_COLOR);
		commandTxt.setForeground(COMMAND_FOREGROUND_COLOR);

		tmpBottom.add(commandScroll);
		
		
		rightPanel.add(tmpRight, BorderLayout.LINE_END);
		bottomPanel.add(tmpBottom, BorderLayout.PAGE_END);
 
		chessboard.setUpNewGame(new Board(Board.NEW_GAME));
		
		
		
		/*for(Square sqr : Square.values())
		{
			System.out.println(sqr + " X:" + sqr.getX() + " Y: " + sqr.getY());
		}*/
		
		 
		// Display the window.
		//frame.pack(); 
		_frame.setResizable(false);
		_frame.pack();
        _frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
	}
 

	private static String listCommands() 
	{
		return "help, reset, edit, resume, save, quit, open, random";
	}


	public static void main(String[] args)
	{
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				System.out.println("Hellp world");
				try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}