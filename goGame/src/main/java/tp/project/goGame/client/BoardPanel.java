package tp.project.goGame.client;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for board. Displays image in the background.
 *
 */
public class BoardPanel extends JLabel{
	Image img;
	ImageIcon icon;
	/**
	 * Constructor, sets background depending on the board size
	 * @param size Size of the board
	 */
	public BoardPanel(int size){

		switch(size)
		{
		case 9:
			icon = new ImageIcon(this.getClass()
		              .getClassLoader().getResource("images/9x9.png"));
			img = icon.getImage();
			img = img.getScaledInstance(360, 360, Image.SCALE_DEFAULT);
			break;
		case 13:
			icon = new ImageIcon(this.getClass()
		              .getClassLoader().getResource("images/13x13.png"));
			img = icon.getImage();
			img = img.getScaledInstance(360, 360, Image.SCALE_DEFAULT);
			break;
		case 19:
			icon = new ImageIcon(this.getClass()
		              .getClassLoader().getResource("images/19x19n.png"));
			img = icon.getImage();
			img = img.getScaledInstance(360, 360, Image.SCALE_AREA_AVERAGING);
			break;
		}
		
		this.setIcon(new ImageIcon(img));
		
	}
	
}
