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

import tp.project.goGame.server.Server;
import tp.project.goGame.shared.Account;
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
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ClientModel {
	private Socket client = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String nickname = null;
	private ClientGUI gui = null;
	
	private GameGUI gameGui = null;
	private BoardGUI boardGUI = null;
	
	private int gameSize;
	private int myColor;
	
	public static void main(String[] args)
	{
		ClientModel clientModel = new ClientModel();
	}
	
	
	ClientModel()
	{
		gui = new ClientGUI(this);
		
		//Establish connection
		try {
			client = new Socket("localhost",7788);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//Start conversation
			new ListenFromServer().start();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't connect to the server");
			e.printStackTrace();
		}
	}
	
	//SEND TO SERVER LINE
	public void sendToServer(String line)
	{
		out.println(line);
	}
	
	public Socket getClient()
	{
		return client;
	}
	
	public String getNickname()
	{
		return this.nickname;
	}
	
	public void setGameSize(int gameSize)
	{
		this.gameSize = gameSize;
	}
	
	public int getGameSize()
	{
		return this.gameSize;
	}
	
	public void sendMove(int x, int y) {
		Request request = new Request(Type.MOVE, Integer.toString(x) + ":" + Integer.toString(y) + ":" + Integer.toString(myColor));
		sendToServer(Protocol.getMessage(request));
	}
	
	public void startNewGame(int size, int color, String nicknameOpponent) {
		myColor = color;
		gui.getFrame().setVisible(false);
		//this.gameGui = new GameGUI(this, gui, nicknameOpponent, myColor, size);	
		boardGUI = new BoardGUI(this,this.gui,nicknameOpponent,color,size);
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
						if(Protocol.getRequest(line).getType() == Type.EXIT)
							break;
						this.proceedAction(line);
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
		
		
		void proceedAction(String line)
		{
			Request input = Protocol.getRequest(line);
			Request out;
			switch(input.getType())
			{
			case ENDGAMEPROMPT:
				//block gui
				int result = gameGui.endGamePrompt(); 
				if(result>0)
				{
					Server.getInstance();
					Server.log("Result: " + result);
					out = new Request(Type.ACCEPT,"");
				}else
				{
					Server.getInstance();
					Server.log("Result: " + result);
					out = new Request(Type.DENY,nickname + " denied.");
				}
				sendToServer(Protocol.getMessage(out));
				break;
			case STARTGAME:
				int i;
				String input2 = input.getValue();
				i=input2.indexOf(':');
				int size = Integer.parseInt(input2.substring(0, i));
				input2 = input2.substring(i+1);
				
				i=input2.indexOf(':');
				String nicknameOpponent = input2.substring(0, i);
				input2 = input2.substring(i+1);
				
				int color = Integer.parseInt(input2);
				
				JOptionPane.showMessageDialog(gui.getFrame(), size + nicknameOpponent + color);
				
				startNewGame(size, color, nicknameOpponent);
				break;
			case LEAVEQUEUE:
				gui.showNewGame();
				break;
			case PASS:
				boardGUI.nextTurn();
				JOptionPane.showMessageDialog(gameGui.getFrame(), input.getValue() + " passed." );
				break;
			case NEWGAME:
				String mode = input.getValue().substring(0, 3);
				if(mode.equals("pvp"))
					gui.showQueue();
				else if(mode.equals("bot"))
				{
					gui.showQueue();
				}
				break;
			case ACCEPT:
				break;
			case DENY:
				if(input.getValue().equals("EGP"))
				{
					//enable gui
				}
				JOptionPane.showMessageDialog(gui.getFrame(),
					    input.getValue(),
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				break;
			case EXIT:
				break;
			case GAMEOVER:
				String input3 = input.getValue();
				String winner = input3.substring(0, input3.indexOf(':'));
				boolean win = nickname.equals(winner);
				gameGui.gameOver(win);
				break;
			case LOGIN:
				nickname = input.getValue();
				gui.setNickname(nickname);
				gui.showLogged();
				break;
			case MESSAGE:
				boardGUI.reciveMessage(input.getValue());
				//JOptionPane.showMessageDialog(gui.getFrame(), input.getValue());
				break;
			case MOVE:
				boardGUI.updateBoard(input.getValue());
				boardGUI.nextTurn();
				break;
			case REGISTER:
				JOptionPane.showMessageDialog(gui.getFrame(), "You've registered correctly!");
				break;
			case WARNING:
				JOptionPane.showMessageDialog(gui.getFrame(), input.getValue(),"Warning",JOptionPane.WARNING_MESSAGE);
				break;
			case LOGOUT:
				JOptionPane.showMessageDialog(null, "You've been logged out!");
				gui.showWelcome();
			default:
				break;
			
			}
		}
	}
}
