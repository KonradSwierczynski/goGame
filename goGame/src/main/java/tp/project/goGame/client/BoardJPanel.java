package tp.project.goGame.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardJPanel extends JPanel {
	
	private BufferedImage image = null;

	public BoardJPanel(GridLayout gridLayout, int size) {
		super(gridLayout);
		/*
		if(size == 9) {
			try {
				image = ImageIO.read(new File("/path/to/image.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(size == 13) {
			try {
				image = ImageIO.read(new File("/path/to/image.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(size == 19) {
			try {
				image = ImageIO.read(new File("/path/to/image.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		this.setBackground(Color.GRAY); //TODO Implement some real image backgound
		
		//g.drawImage(image, 0, 0, null);
		
	}
}
