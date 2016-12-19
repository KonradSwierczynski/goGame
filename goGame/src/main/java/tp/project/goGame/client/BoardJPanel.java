package tp.project.goGame.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardJPanel extends JPanel {

	public BoardJPanel(GridLayout gridLayout) {
		super(gridLayout);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		this.setBackground(Color.GRAY); //TODO Implement some real image backgound
		/*
		try {
			File pathToFile = new File("/goGame/resources/images/board9X9.png");
			Image image = ImageIO.read(pathToFile);
			g.drawImage(image, 0, 0, null);
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		*/
	}
}
