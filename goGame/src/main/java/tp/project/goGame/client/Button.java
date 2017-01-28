package tp.project.goGame.client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Lucent button providing icon changing.
 * Used for single square in board.
 */
public class Button extends JButton{
	Image img = null;
	ImageIcon icon = null;
	private static int pixelSize;
	
	private int x,y;
	
	/**
	 * Sets coordinates of the button.
	 * @param x	First coordinate
	 * @param y Second coordinate
	 */
	public Button(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
		
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}
	
	/**
	 * Chamges color of the button and icon.
	 * @param i New color of the button.
	 */
	public void setColor(int i)
	{
		if(i==1)
		{
			try {
				img = ImageIO.read(this.getClass()
			              .getClassLoader().getResource("images/blackStone.png"));
				img = img.getScaledInstance(this.pixelSize, this.pixelSize, Image.SCALE_DEFAULT);
				icon = new ImageIcon(img);
				this.setOpaque(true);
				this.setContentAreaFilled(true);
				this.setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==2){
			try {
				img = ImageIO.read(this.getClass()
			              .getClassLoader().getResource("images/whiteStone.png"));
				img = img.getScaledInstance(this.pixelSize, this.pixelSize, Image.SCALE_DEFAULT);
				icon = new ImageIcon(img);
				this.setOpaque(true);
				this.setContentAreaFilled(true);
				this.setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(i==0)
		{
			this.setBackground(null);
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			this.setIcon(null);
		}else if(i==3)
		{
			this.setBackground(Color.MAGENTA);
			this.setOpaque(true);
			this.setContentAreaFilled(true);
			this.setBorderPainted(false);
			this.setIcon(null);
		}else if(i==4)
		{
			this.setBackground(Color.BLUE);
			this.setOpaque(true);
			this.setContentAreaFilled(true);
			this.setBorderPainted(false);
			this.setIcon(null);
		}
		
	}
	
	public void setPixelSize(int size)
	{
		this.pixelSize = 360/size;
	}
	
	public int getx()
	{
		return this.x;
	}
	
	public int gety()
	{
		return this.y;
	}
	
	public void addListener(ActionListener AL)
	{
		this.addActionListener(AL);
	}
	
}



