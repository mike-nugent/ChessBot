package values;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CapturedPieceIcon extends ExtendedJPanel 
{
	private JLabel _arrow;
	public CapturedPieceIcon(String pieceTxt)
	{
		  ImageIcon image = null; 
	        try
	        {
	         image = new ImageIcon(
					ImageIO.read(
							new File("src/main/resources/"+pieceTxt+".png"))
					.getScaledInstance(-1, 30, Image.SCALE_SMOOTH));
			
	        } catch (Exception e)
	        {
	        	System.out.println("error in " + this);
	        	e.printStackTrace();
	        }
	        _arrow = new JLabel(image); 
	        _arrow.setLayout(null);
	        this.setLayout(null);
	        _arrow.setBounds(0, 0, image.getIconWidth(), image.getIconHeight() );
	        _arrow.setMaximumSize(new Dimension(30, 55));
	        this.setMaximumSize(new Dimension(30, 55));
	        this.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
			this.add(_arrow);
			this.setOpaque(false);
			_arrow.setOpaque(false);
	}
} 
