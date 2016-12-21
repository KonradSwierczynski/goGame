package tp.project.goGame.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlainJButton extends JButton {
	private int X;
	private int Y;
	
	private Image backgroundImage = null;
	
	public PlainJButton (int x, int y){
        super();
        this.X = x;
        this.Y = y;
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
	
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}
	
	public void setStone(int color) {

		backgroundImage = null;
		/*
		if(color == 1) {
		      try {
		          BufferedImage background = ImageIO.read(new File("/path/to/image.jpg"));
		          backgroundImage = background;
		        } catch (IOException ex) {
		          ex.printStackTrace();
		        }
		}
		if(color == 2) {
		      try {
		          BufferedImage background = ImageIO.read(new File("/path/to/image.jpg"));
		          backgroundImage = background;
		        } catch (IOException ex) {
		          ex.printStackTrace();
		        }
		}
		*/
		
		if(this.backgroundImage != null)
	    	this.setIcon(new ImageIcon(backgroundImage));
	}
	
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if(this.backgroundImage != null)
    	  this.setIcon(new ImageIcon(backgroundImage));
    }
}
