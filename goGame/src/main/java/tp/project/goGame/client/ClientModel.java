package tp.project.goGame.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ClientModel {
	private Socket client = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	//GUI part
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
	
	public static void main(String[] args)
	{
		ClientModel clientModel = new ClientModel();
		clientModel.frame.setVisible(true);
	}
	
	
	ClientModel()
	{
		try {
			client = new Socket("localhost",7788);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			new ListenFromServer().start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//GUI part
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
		lblUsername.setBounds(10, 51, 47, 14);
		loginSection.add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(10, 95, 46, 14);
		loginSection.add(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Request temp =  new Request(Type.LOGIN,usernameField.getText()+":"+passwordField.getText()+"::");
				out.println(Protocol.getMessage(temp));
			}
		});
		btnLogIn.setBounds(49, 164, 89, 23);
		loginSection.add(btnLogIn);
		
		JButton btnNewButton = new JButton("REGISTER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)panel.getLayout();
				cl.show(panel, "register");
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
				out.println(Protocol.getMessage(temp));
				
			}
		});
		btnRegister.setBounds(335, 228, 89, 23);
		panel_reg.add(btnRegister);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)panel.getLayout();
				cl.show(panel, "welcome");
			}
		});
		btnBack.setBounds(10, 228, 89, 23);
		panel_reg.add(btnBack);
		
		panel_logged = new JPanel();
		panel.add(panel_logged, "logged");
		panel_logged.setLayout(null);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(161, 66, 89, 23);
		panel_logged.add(btnNewGame);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(335, 228, 89, 23);
		panel_logged.add(btnQuit);
		
		
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                out.println("EXT" + "client: " + client.getInetAddress() + " disconnected");
                e.getWindow().dispose();
            }
        });
	}
	
	class ListenFromServer extends Thread
	{
		public void run()
		{
			try{
				
				String line;
				while(true)
				{
					
					line = in.readLine();
					if(line != null)
					{
						if(Protocol.getRequest(line).getType().equals(Type.ACCEPT) && (Protocol.getRequest(line).getValue().equals("pass")))
						{
							CardLayout cl = (CardLayout)panel.getLayout();
							cl.show(panel, "logged");
						}
						if(Protocol.getRequest(line).getType() == Type.EXIT)
							break;
					}
				}
				
				in.close();
				out.close();
				client.close();
			}catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
