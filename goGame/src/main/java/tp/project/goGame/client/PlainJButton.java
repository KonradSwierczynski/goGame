package tp.project.goGame.client;

import javax.swing.JButton;

public class PlainJButton extends JButton {
	private int X;
	private int Y;

	public PlainJButton (String text, int x, int y){
        super(text);
        this.X = x;
        this.Y = y;
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
	
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
}
