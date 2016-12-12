package tp.project.goGame.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

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
		frame.setVisible(true);
		
		logArea = new JTextArea();
		logArea.setBounds(10, 49, 347, 202);
		frame.getContentPane().add(logArea);
	}
	
	public void addLog(String message)
	{
		logArea.append(message + "\n");
	}
}
