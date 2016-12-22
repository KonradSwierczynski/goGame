package tp.project.goGame.client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.Font;

public class ClientGUI implements ActionListener {

	private ClientModel clientModel = null;

	private JFrame frame;
	private JPanel panel;
	private JPanel panel_wel;
	private JPanel panel_reg;
	private JPanel panel_logged;
	private JTextField usernameField;
	private JTextField passwordField;
	private JTextField reg_usernameField;
	private JTextField reg_passwordField;
	private JTextField reg_emailReg;
	private JTextField reg_nicknameField;
	private JCheckBox chckbxBot;
	private JLabel lblnick;

	public ClientGUI(final ClientModel clientModel) {	
		this.clientModel = clientModel;
		initialize();
		frame.setVisible(true);
	}
	
	void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 434, 262);
		frame.getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		panel_wel = new JPanel();
		panel.add(panel_wel, "welcome");
		panel_wel.setLayout(null);
		
		JPanel loginSection = new JPanel();
		loginSection.setBorder(new LineBorder(new Color(0, 0, 0)));
		loginSection.setBounds(238, 11, 186, 240);
		panel_wel.add(loginSection);
		loginSection.setLayout(null);
		
		JLabel lblLogIn = new JLabel("log in");
		lblLogIn.setBounds(10, 8, 66, 14);
		loginSection.add(lblLogIn);
		
		usernameField = new JTextField();
		usernameField.setBounds(10, 64, 86, 20);
		loginSection.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(10, 108, 86, 20);
		loginSection.add(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(10, 51, 86, 14);
		loginSection.add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 95, 86, 14);
		loginSection.add(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clientModel.startNewGame(19, 1, "debug");
				//Request temp =  new Request(Type.LOGIN,usernameField.getText()+":"+passwordField.getText()+"::");
				//clientModel.sendToServer(Protocol.getMessage(temp));
			}
		});
		btnLogIn.setBounds(49, 164, 89, 23);
		loginSection.add(btnLogIn);
		
		JButton btnNewButton = new JButton("REGISTER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showRegister();
			}
		});
		btnNewButton.setBounds(21, 86, 186, 72);
		panel_wel.add(btnNewButton);
		
		panel_reg = new JPanel();
		panel.add(panel_reg, "register");
		panel_reg.setLayout(null);
		
		reg_usernameField = new JTextField();
		reg_usernameField.setBounds(10, 54, 280, 20);
		panel_reg.add(reg_usernameField);
		reg_usernameField.setColumns(10);
		
		reg_passwordField = new JTextField();
		reg_passwordField.setBounds(10, 100, 280, 20);
		panel_reg.add(reg_passwordField);
		reg_passwordField.setColumns(10);
		
		reg_emailReg = new JTextField();
		reg_emailReg.setBounds(10, 143, 280, 20);
		panel_reg.add(reg_emailReg);
		reg_emailReg.setColumns(10);
		
		reg_nicknameField = new JTextField();
		reg_nicknameField.setBounds(10, 192, 280, 20);
		panel_reg.add(reg_nicknameField);
		reg_nicknameField.setColumns(10);
		
		JLabel lblUsername_1 = new JLabel("username");
		lblUsername_1.setBounds(10, 41, 280, 14);
		panel_reg.add(lblUsername_1);
		
		JLabel lblPassword_1 = new JLabel("password");
		lblPassword_1.setBounds(10, 87, 280, 14);
		panel_reg.add(lblPassword_1);
		
		JLabel lblEmail = new JLabel("email");
		lblEmail.setBounds(10, 131, 280, 14);
		panel_reg.add(lblEmail);
		
		JLabel lblNickname = new JLabel("nickname");
		lblNickname.setBounds(10, 181, 280, 14);
		panel_reg.add(lblNickname);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request temp = new Request(Type.REGISTER,reg_usernameField.getText() + ":" + reg_passwordField.getText() + ":" + reg_emailReg.getText() + ":" + reg_nicknameField.getText());
				clientModel.sendToServer(Protocol.getMessage(temp));
				
			}
		});
		btnRegister.setBounds(335, 228, 89, 23);
		panel_reg.add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWelcome();
			}
		});
		btnBack.setBounds(10, 228, 89, 23);
		panel_reg.add(btnBack);
		
		panel_logged = new JPanel();
		panel.add(panel_logged, "logged");
		panel_logged.setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNewGame();
			}
		});
		btnNewGame.setBounds(10, 11, 166, 88);
		panel_logged.add(btnNewGame);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Request out = new Request(Type.LOGOUT,clientModel.getNickname());
				clientModel.sendToServer(Protocol.getMessage(out));
			}
		});
		btnQuit.setBounds(335, 228, 89, 23);
		panel_logged.add(btnQuit);
		
		lblnick = new JLabel("bartek");
		lblnick.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblnick.setBounds(258, 11, 166, 23);
		panel_logged.add(lblnick);
		
		JPanel panel_choice = new JPanel();
		panel.add(panel_choice, "choice");
		panel_choice.setLayout(null);
		
		JRadioButton radioButton = new JRadioButton("13x13");
		radioButton.setActionCommand("13");
		radioButton.setBounds(282, 108, 109, 23);
		panel_choice.add(radioButton);
		radioButton.addActionListener(this);
		
		JRadioButton radioButton_1 = new JRadioButton("19x19");
		radioButton_1.setActionCommand("19");
		radioButton_1.setBounds(282, 66, 109, 23);
		panel_choice.add(radioButton_1);
		radioButton_1.addActionListener(this);
		
		JRadioButton radioButton_2 = new JRadioButton("9x9");
		radioButton_2.setActionCommand("9");
		radioButton_2.setBounds(282, 153, 109, 23);
		panel_choice.add(radioButton_2);
		radioButton_2.addActionListener(this);
		
		chckbxBot = new JCheckBox("bot");
		chckbxBot.setBounds(294, 199, 97, 23);
		panel_choice.add(chckbxBot);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request temp;
				if(chckbxBot.isSelected())
					temp = new Request(Type.NEWGAME,"bot" + clientModel.getGameSize());
				else
					temp = new Request(Type.NEWGAME,"pvp" + clientModel.getGameSize());
				
				clientModel.sendToServer(Protocol.getMessage(temp));
			}
		});
		btnStart.setBounds(161, 87, 89, 23);
		panel_choice.add(btnStart);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton_1);
		group.add(radioButton_2);
		group.add(radioButton);
		
		JButton btnNewButton_1 = new JButton("back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLogged();
			}
		});
		btnNewButton_1.setBounds(10, 228, 89, 23);
		panel_choice.add(btnNewButton_1);
		
		JPanel panel_queue = new JPanel();
		panel.add(panel_queue, "queue");
		panel_queue.setLayout(null);
		
		JLabel lblInQueue = new JLabel("IN QUEUE");
		lblInQueue.setFont(new Font("Tahoma", Font.PLAIN, 57));
		lblInQueue.setBounds(78, 37, 375, 132);
		panel_queue.add(lblInQueue);
		
		JButton btnLeaveQueue = new JButton("Leave Queue");
		btnLeaveQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request temp = new Request(Type.LEAVEQUEUE,"leave");
				clientModel.sendToServer(Protocol.getMessage(temp));
			}
		});
		btnLeaveQueue.setBounds(284, 200, 140, 39);
		panel_queue.add(btnLeaveQueue);
		
		
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                clientModel.sendToServer("EXT" + "client disconnected");
                e.getWindow().dispose();
            }
        });
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public void setNickname(String nickname)
	{
		lblnick.setText("Logged as: " + nickname);
	}
	
	
	public void showLogged()
	{
		CardLayout cl = (CardLayout)panel.getLayout();
		cl.show(panel, "logged");
	}
	
	public void showWelcome()
	{
		CardLayout cl = (CardLayout)panel.getLayout();
		cl.show(panel, "welcome");
	}
	
	public void showRegister()
	{
		CardLayout cl = (CardLayout)panel.getLayout();
		cl.show(panel, "register");
	}
	
	public void showNewGame()
	{
		CardLayout cl = (CardLayout)panel.getLayout();
		cl.show(panel, "choice");
	}
	
	public void showQueue()
	{
		CardLayout cl = (CardLayout)panel.getLayout();
		cl.show(panel, "queue");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clientModel.setGameSize(Integer.parseInt(e.getActionCommand()));
	}
}
