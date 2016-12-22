package tp.project.goGame.client;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	Image img;
	public BoardPanel(int size){

		try {
			switch(size)
			{
			case 9:
				img = ImageIO.read(new File("resources/images/9x9.png"));
				img = img.getScaledInstance(360, 360, Image.SCALE_DEFAULT);
				break;
			case 13:
				img = ImageIO.read(new File("resources/images/13x13.png"));
				img = img.getScaledInstance(360, 360, Image.SCALE_DEFAULT);
				break;
			case 19:
				img = ImageIO.read(new File("resources/images/19x19.png"));
				img = img.getScaledInstance(360, 360, Image.SCALE_DEFAULT);
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(img, 0, 0, null);
	}
	
	
}
