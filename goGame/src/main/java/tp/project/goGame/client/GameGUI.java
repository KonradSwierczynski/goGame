package tp.project.goGame.client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.GameSize;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JTable;
import java.awt.Panel;
import java.awt.Button;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;

public class GameGUI {

	private ClientModel clientModel;
	private ClientGUI clientGUI;
	private Board boardGame;
	
	private int size;
	private String opponentNick;
	private int myColor;

	private JFrame frame;
	private JPanel board;
	private PlainJButton[][] boardSquares = new PlainJButton[9][9];
	private JButton btnPass;
	private JButton btnExit;
	private JButton btnSendMessage;
	private JTextArea textAreaMessages;
	private JTextField textFieldNewMessage;
	private JLabel lbBoardBackround;

	public GameGUI(final ClientModel clientModel, ClientGUI clientGUI, String opponentNick, int myColor, int size) {
		this.clientModel = clientModel;
		this.clientGUI = clientGUI;
		this.opponentNick = opponentNick;
		this.myColor = myColor;
		this.size = size;
		clientGUI.getFrame().setVisible(false);
		initialize();
		frame.setVisible(true);
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 894, 758);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel gamePanel = new JPanel();
		frame.getContentPane().add(gamePanel);
		GridBagLayout gbl_gamePanel = new GridBagLayout();
		gbl_gamePanel.rowWeights = new double[]{1.0, 0.0, 0.0};
		gbl_gamePanel.columnWeights = new double[]{0.0, 1.0};
		gamePanel.setLayout(gbl_gamePanel);	
		
		board = new BoardJPanel(new GridLayout(0, 9));
		board.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
                ));
		GridBagConstraints gbc_board = new GridBagConstraints();
		gbc_board.insets = new Insets(0, 0, 5, 5);
		gbc_board.gridx = 0;
		gbc_board.gridy = 0;
		gamePanel.add(board, gbc_board);
		
		textAreaMessages = new JTextArea();
		textAreaMessages.setEditable(false);
		GridBagConstraints gbc_textAreaMessages = new GridBagConstraints();
		gbc_textAreaMessages.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaMessages.fill = GridBagConstraints.BOTH;
		gbc_textAreaMessages.gridx = 1;
		gbc_textAreaMessages.gridy = 0;
		gamePanel.add(textAreaMessages, gbc_textAreaMessages);
		
		btnPass = new JButton("Pass");
		btnPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pass();
			}
		});
		GridBagConstraints gbc_btnPass = new GridBagConstraints();
		gbc_btnPass.insets = new Insets(0, 0, 5, 5);
		gbc_btnPass.gridx = 0;
		gbc_btnPass.gridy = 1;
		gamePanel.add(btnPass, gbc_btnPass);
		
		textFieldNewMessage = new JTextField();
		GridBagConstraints gbc_textFieldNewMessage = new GridBagConstraints();
		gbc_textFieldNewMessage.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNewMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNewMessage.gridx = 1;
		gbc_textFieldNewMessage.gridy = 1;
		gamePanel.add(textFieldNewMessage, gbc_textFieldNewMessage);
		textFieldNewMessage.setColumns(10);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 2;
		gamePanel.add(btnExit, gbc_btnExit);
		
		btnSendMessage = new JButton("Send Message");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			}
		});
		GridBagConstraints gbc_btnSendMessage = new GridBagConstraints();
		gbc_btnSendMessage.gridx = 1;
		gbc_btnSendMessage.gridy = 2;
		gamePanel.add(btnSendMessage, gbc_btnSendMessage);
		
		Insets buttonMargin = new Insets(0,0,0,0);
	    for (int i = 0; i < boardSquares.length; i++) {
	    	for (int j = 0; j < boardSquares[i].length; j++) {
	     		PlainJButton button = new PlainJButton(j, i);
	     		//button.setMargin(buttonMargin);
	     		button.setSize(65, 65);
	     		ImageIcon icon = new ImageIcon(
                        new BufferedImage(65, 65, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(icon);
	     		button.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent arg0) {
	    				makeMove(((PlainJButton) arg0.getSource()).getX(), ((PlainJButton) arg0.getSource()).getY());
	    			}
	    		});
	     		boardSquares[j][i] = button;
	     		board.add(boardSquares[j][i]);
	        }
	    }
	}
	
	public void reciveMessage(String message) {
		this.textAreaMessages.append("\n>" + message);
	}
	
	public void updateBoard(String boardString) {
		boardGame.restoreBoard(boardString);
		int position = 0;
		for(int i = 0; i < boardGame.getIntSize(); i++) {
			for(int j = 0; j < boardGame.getIntSize(); j++) {
				int colorOfPosition = Character.getNumericValue(boardString.charAt(position));
				//boardSquares[i][j].setIcon();
				position += 2;
			}
			position += 1;
		}
	}
	
	public void gameOver(boolean win) {
		if(win)
			JOptionPane.showMessageDialog(frame, "You won");
		else
			JOptionPane.showMessageDialog(frame, opponentNick + " won");
		
		frame.setVisible(false);
		clientGUI.getFrame().setVisible(true);
	}
	
	private void sendMessage() {
		Request request = new Request(Type.MESSAGE, textFieldNewMessage.getText());
		clientModel.sendToServer(Protocol.getMessage(request));
		textFieldNewMessage.setText("");
	}
	
	private void makeMove(int x, int y) {
		System.out.println(Integer.toString(x) + " " + Integer.toString(y));
		clientModel.sendMove(x, y);
	}
	
	private void pass() {
		Request request = new Request(Type.PASS, clientModel.getNickname());
		clientModel.sendToServer(Protocol.getMessage(request));
	}
	
	private void exitGame() {
		Request request = new Request(Type.GAMEOVER, clientModel.getNickname());
		clientModel.sendToServer(Protocol.getMessage(request));
	}
	
}
