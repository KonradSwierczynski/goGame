package tp.project.goGame.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

public class BoardGUI{
	
	/*
	 * logic
	 */
	private ClientModel clientModel;
	private ClientGUI clientGUI;
	private String opponentNick;
	private int myColor;
	private boolean myTurn = true;

	/*
	 * GUI
	 */
	private JFrame frame;
	private Button[][] buttons;
	private JTextField textField;
	private JTextArea textArea;
	private static int size;
	private JButton btnPass;
	
	

	public BoardGUI(ClientModel clientModel, ClientGUI clientGUI, String opponentNick, int myColor, int size) {
		this.clientModel = clientModel;
		this.clientGUI = clientGUI;
		this.opponentNick = opponentNick;
		this.myColor = myColor;
		this.size = size;
		
		if(myColor == 1)
			myTurn = true;
		else
			myTurn = false;
		
		
		initialize();
	}
	
	public void reciveMessage(String message) {
		this.textArea.append(message + "\n");
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
		
		textArea = new JTextArea();
		textArea.setBounds(380, 11, 175, 295);
		frame.getContentPane().add(textArea);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds(380, 348, 78, 23);
		frame.getContentPane().add(btnSend);
		btnSend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientModel.sendToServer(Protocol.getMessage(new Request(Type.MESSAGE,clientModel.getNickname() + ">"+ textField.getText())));
			}
		});
		
		btnPass = new JButton("PASS");
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
				buttons[i][j] = new Button(i,j);
				buttons[i][j].setPixelSize(size);
				//System.out.println(buttons[i][j].getLocation().toString());
				buttons[i][j].addListener(new ButtonListener());
				panel.add(buttons[i][j]);
			}
		}
	
		frame.setVisible(true);
	}
	
	public void disableGUI()
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				buttons[i][j].setEnabled(false);
			}
		}
		btnPass.setEnabled(false);
	}
	
	public void enableGUI()
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				buttons[i][j].setEnabled(true);
				
			}
		}
		btnPass.setEnabled(true);
	}
	
	
	public void nextTurn()
	{
		myTurn = !myTurn;
	}
	
	public JFrame getFrame()
	{
		return this.frame;
	}
	
	public void updateBoard(String boardString) {
		int position = 3;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				buttons[i][j].setColor(Character.getNumericValue(boardString.charAt(position)));
				position += 2;
			}
			
			position += 1;
		}
	}
	
	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(myTurn)
			{
				Button button = (Button)arg0.getSource();
				clientModel.sendMove(button.getx(), button.gety());
			}else
				JOptionPane.showMessageDialog(getFrame(), "not your turn!");
		}
		
	}
}
