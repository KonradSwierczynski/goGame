package tp.project.goGame.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.awt.Font;
import javax.swing.JPasswordField;

public class ServerGUI {

	private JFrame frame;
	private JTextArea logArea;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public ServerGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 632, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		logArea = new JTextArea();
		logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		logArea.setBounds(10, 49, 347, 341);
		logArea.setEditable(false);
		//frame.getContentPane().add(logArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 596, 341);
		scrollPane.setViewportView(logArea);
		frame.getContentPane().add(scrollPane);
		
		
		frame.setVisible(true);	
	}
	
	public void addLog(String message)
	{
		logArea.append(message + "\n");
	}
}
