package tp.project.goGame.client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Main panel for client aplication GUI
 *
 */
public class MainPanel extends JPanel{
	BufferedImage stop = null;
	
	public MainPanel()
	{
		try {
			stop = ImageIO.read(new File("resources/images/stop.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(stop, 390, 347, 24, 24, this);
    }
}
