package tp.project.goGame.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;

public class ServerGUI {

	private JFrame frame;
	private JTextArea logArea;

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		logArea = new JTextArea();
		logArea.setBounds(10, 49, 347, 202);
		frame.getContentPane().add(logArea);
		
		
		
		
		frame.setVisible(true);	
	}
	
	public void addLog(String message)
	{
		logArea.append(message + "\n");
	}
}
