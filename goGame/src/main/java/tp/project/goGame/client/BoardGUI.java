package tp.project.goGame.client;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

public class BoardGUI {

	private JFrame frame;
	private Button[][] buttons;
	private JTextField textField;
	private static int size;

	public BoardGUI(int size) {
		this.size = size;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 581, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		BoardPanel panel = new BoardPanel(size);
		panel.setBounds(10, 11, 360, 360);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(size,size));
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(380, 11, 175, 295);
		frame.getContentPane().add(textArea);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds(380, 348, 78, 23);
		frame.getContentPane().add(btnSend);
		
		JButton btnPass = new JButton("PASS");
		btnPass.setBounds(477, 348, 78, 23);
		frame.getContentPane().add(btnPass);
		
		textField = new JTextField();
		textField.setBounds(380, 317, 175, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		buttons = new Button[size][];
		for(int i=0;i<size;i++)
		{
			buttons[i] = new Button[size];
			for(int j=0;j<size;j++)
			{
				buttons[i][j] = new Button();
				buttons[i][j].setPixelSize(size);
				panel.add(buttons[i][j]);
			}
		}
		
		frame.setVisible(true);
	}
}
