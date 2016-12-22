package tp.project.goGame.client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{
	Image img = null;
	ImageIcon icon = null;
	private static int pixelSize;
	
	public Button()
	{
		super();
		this.addActionListener(new myAL());
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}
	
	public void setColor(int i)
	{
		if(i==1)
		{
			try {
				img = ImageIO.read(new File("resources/images/blackStone.png"));
				img = img.getScaledInstance(this.pixelSize, this.pixelSize, Image.SCALE_DEFAULT);
				icon = new ImageIcon(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				img = ImageIO.read(new File("resources/images/whiteStone.png"));
				img = img.getScaledInstance(this.pixelSize, this.pixelSize, Image.SCALE_DEFAULT);
				icon = new ImageIcon(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setOpaque(true);
		this.setContentAreaFilled(true);
		this.setIcon(icon);
	}
	
	public void setPixelSize(int size)
	{
		this.pixelSize = 360/size;
	}
	
	class myAL implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			setColor(2);
		}
		
	}
	
}



