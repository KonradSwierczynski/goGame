package tp.project.goGame.client;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
/**
 * Class to display and control game.
 * Displays board and chat, provide action handlers.
 * Communicate with CilentModel.
 * @see ClienModel
 * @see	ClientGUI
 *
 */
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
	private static int size = 9;
	private JButton btnPass;
	private JPanel panel_1;
	private JLabel lblYou;
	private JLabel lblNewLabel_1;
	private JButton btnBot;
	
	public boolean waitForAnswer = false;
	private boolean bot;
	
	
	/**
	 * Constructor, uses dependency injection.
	 * @param clientModel	Class to communicate with server.
	 * @param clientGUI	Instance of ClientGUI used by user.
	 * @param opponentNick	Nick of opponent in current game.
	 * @param myColor	My color of stone in the game.
	 * @param size	Size of the game.
	 * @param bot	True if playing with bot.
	 */
	public BoardGUI(ClientModel clientModel, ClientGUI clientGUI, String opponentNick, int myColor, int size,boolean bot) {
		this.clientModel = clientModel;
		this.clientGUI = clientGUI;
		this.opponentNick = opponentNick;
		this.myColor = myColor;
		this.size = size;
		this.bot = bot;
		System.out.println(this.bot);
		
		initialize();
		
		if(!bot)
			btnBot.setVisible(false);
		panel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setText(opponentNick);
	
		frame.setTitle(size + "x" + size +" - go Game");
		
		if(myColor == 1)
		{
			lblYou.setText("YOU");
			myTurn = true;
		}
		else
		{
			lblYou.setText("OPP");
			myTurn = false;
		}
		
		
	}
	
	/**
	 * Getter for opponent nick
	 * @return Opponent nick
	 */
	public String getOpponentNickname()
	{
		return this.opponentNick;
	}
	
	/**
	 * Append area for messages with new message.
	 * @param message	New message
	 */
	public void reciveMessage(String message) {
		this.textArea.append(message + "\n");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 581, 426);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new ExitListener());
		
		BoardPanel panel = new BoardPanel(9);
		panel.setBounds(10, 11, 360, 360);
		panel.setLayout(new GridLayout(9,9));
		frame.getContentPane().add(panel);
		
		btnBot = new JButton("End");
		btnBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm;
				confirm = JOptionPane.showOptionDialog(
		   	             null, "Are You Sure you Want to end this Game?", 
		   	             "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
		   	             JOptionPane.QUESTION_MESSAGE, null, null, null);
	        	System.out.println(confirm);
	        	if(confirm == 0)
	        	{
	        		clientModel.sendToServer(Protocol.getMessage(new Request(Type.CONCEDE,"bot")));
	        	}
			}
		});
		btnBot.setBounds(426, 371, 87, 17);
		frame.getContentPane().add(btnBot);
		
		
		textArea = new JTextArea();
		textArea.setBounds(380, 88, 175, 218);
		frame.getContentPane().add(textArea);
		textArea.setEditable(false);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds(380, 348, 78, 23);
		frame.getContentPane().add(btnSend);
		btnSend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().equals(""))
					clientModel.sendToServer(Protocol.getMessage(new Request(Type.MESSAGE,clientModel.getNickname() + ">"+ textField.getText())));
			}
		});
		
		btnPass = new JButton("PASS");
		btnPass.setBounds(477, 348, 78, 23);
		frame.getContentPane().add(btnPass);
		btnPass.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(myTurn)
				{
					clientModel.sendToServer(Protocol.getMessage(new Request(Type.PASS,clientModel.getNickname())));
				}else
					JOptionPane.showMessageDialog(getFrame(), "not your turn!");
				
			}
			
		});
		
		textField = new JTextField();
		textField.setBounds(380, 317, 175, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		panelInfo.setBounds(380, 11, 175, 66);
		frame.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Opponent:");
		lblNewLabel.setBounds(10, 7, 76, 14);
		panelInfo.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Bartol");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 22, 76, 14);
		panelInfo.add(lblNewLabel_1);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(86, 7, 46, 14);
		panelInfo.add(lblColor);
		
		panel_1 = new JPanel();
		panel_1.setBounds(86, 30, 79, 25);
		panelInfo.add(panel_1);
		
		lblYou = new JLabel("YOU");
		lblYou.setForeground(Color.RED);
		panel_1.add(lblYou);
		
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
	
	/**
	 * Disables board.
	 */
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
	
	/**
	 * Enables board.
	 */
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
	
	/**
<<<<<<< HEAD
	 * Flips turn
=======
	 * Changes turn to next player.
>>>>>>> branch 'master' of https://github.com/bartoszgralek/goGame.git
	 */
	public void nextTurn()
	{
		myTurn = !myTurn;
		
		if(myTurn)
		{
			if(myColor == 1)
			{
				lblYou.setText("YOU");
				panel_1.setBackground(Color.BLACK);
			}else
			{
				lblYou.setText("YOU");
				panel_1.setBackground(Color.WHITE);
			}
		}else
		{
			if(myColor == 1)
			{
				lblYou.setText("OPP");
				panel_1.setBackground(Color.WHITE);
			}else
			{
				lblYou.setText("OPP");
				panel_1.setBackground(Color.BLACK);
			}
		}
	}
	
	/**
	 * Getter for frame.
	 * @return	Main frame.
	 */
	public JFrame getFrame()
	{
		return this.frame;
	}
	
	/**
	 * Displays recived board.
	 * @param boardString Board to display stored in String
	 */
	public void updateBoard(String boardString) {
		System.out.println(boardString);
		int position = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				buttons[i][j].setColor(Character.getNumericValue(boardString.charAt(position)));
				position += 2;
			}
			
			position += 1;
		}
	}

	/**
	 * Ensure that player wants to end the game.
	 * @return	True if player wants to end the game.
	 */
	public int endGamePrompt()
	{
		return JOptionPane.showConfirmDialog((Component)null, "End the game?","End game prompt",JOptionPane.YES_NO_OPTION);
	}
	
	/**
<<<<<<< HEAD
	 * Dialog with results
	 * @param win if I won?
	 * @param pBlack points of Black
	 * @param pWhite point of White
=======
	 * Ends the game, closes window and displays ClientGUI
	 * @param win	True if player won
	 * @param pBlack	Number of player's with black stones points
	 * @param pWhite	Number of player's with white stones points
>>>>>>> branch 'master' of https://github.com/bartoszgralek/goGame.git
	 */
	public void gameOver(boolean win,float pBlack, float pWhite) {
		if(win)
			JOptionPane.showMessageDialog(frame, "You won\n" + "BLACK: " + pBlack + " WHITE: " + pWhite);
		else
			JOptionPane.showMessageDialog(frame, opponentNick + " won\n" + "BLACK: " + pBlack + " WHITE: " + pWhite);
		
		frame.setVisible(false);
		frame.dispose();
		clientGUI.showNewGame();
		clientGUI.getFrame().setVisible(true);
	}
	
	/**
	 * Action handler for buttons
	 *
	 */
	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(myTurn)
			{
				Button button = (Button)arg0.getSource();
				clientModel.sendMove(button.getx(), button.gety());
				System.out.println(button.getx() + "-" + button.gety());
			}else
				JOptionPane.showMessageDialog(getFrame(), "not your turn!");
		}
		
	}
	
	/**
	 * Class to handle concede or exit(when playing with bot)
	 * @author Bartek
	 *
	 */
	class ExitListener extends WindowAdapter {

	    @Override
	    public void windowClosing(WindowEvent e) {
	        int confirm;
	       
	        
	        	confirm = JOptionPane.showOptionDialog(
	   	             null, "Are You Sure you Want to Concede?", 
	   	             "Exit Confirmation", JOptionPane.YES_NO_OPTION, 
	   	             JOptionPane.QUESTION_MESSAGE, null, null, null);
	        	System.out.println(confirm);
	        	if (confirm == 0) {
	        		clientModel.sendToServer(Protocol.getMessage(new Request(Type.CONCEDE,"pvp")));
	 	        }
	        
	        
	    }
	}
}
