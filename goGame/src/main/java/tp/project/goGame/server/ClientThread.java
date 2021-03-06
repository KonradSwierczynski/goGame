package tp.project.goGame.server;

import tp.project.goGame.shared.Account;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import tp.project.goGame.state.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;

public class ClientThread extends Thread{
	private BufferedReader in = null;
	private PrintWriter out = null;
	private Socket clientSocket = null;
	private Account account = null;
	private MyState myState = null;
	private int gameSize;
	private GameThread currentGame = null;
	
	public ClientThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
		this.myState = new ConnectedState();
	}
	
	public void run(){
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			
			while(true)
			{
				String line;
				String outLine;
				line = in.readLine();
				
				//log(clientSocket, Protocol.getRequest(line).getType() + ": " + Protocol.getRequest(line).getValue());
				log(clientSocket,line + this.myState.toString());
				outLine = Protocol.getMessage(proceedAction(line));
				out.println(outLine);
				
				if(Protocol.getRequest(line).getType() == Type.EXIT)
					break;
			}

			//Close connection
			in.close();
			out.close();
			clientSocket.close();
			Server.getInstance().closeThread(this);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * This method performs certain action depending on request type
	 * @param line input line
	 * @return request Request Accept/Deny to inform player about action outcome
	 */
	public Request proceedAction(String line)
	{
		Request request = Protocol.getRequest(line);
		Request outRequest = null;
		switch(request.getType())
		{
		case CONCEDE:
			this.myState.ChangeState(this, new LoggedInState());
			if(request.getValue().equals("bot"))
			{
			outRequest = new Request(Type.GAMEOVER,currentGame.getWinner() + ":" + currentGame.getBlackScore() + ":" + currentGame.getWhiteScore());
			currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			}else
			{
				currentGame.QuitGamePlayers();
				outRequest = new Request(Type.GAMEOVER,currentGame.getOpponentNickname(this) + ":" + currentGame.getBlackScore() + ":" + currentGame.getWhiteScore());
				currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			}
			break;
		case STARTGAME:
			outRequest = myState.StartGame(this, request.getValue());
			break;
		case LEAVEQUEUE:
			outRequest = myState.QuitQueue(this);
			break;
		case PASS:
			outRequest = currentGame.makePass(this);
			break;
		case NEWGAME:
			
			outRequest = myState.PlayGame(this, request.getValue());
			
			if(request.getValue().substring(0, 3).equals("pvp"))
				Server.getInstance().checkQueue(this, this.getGameSize());
			
			
			log(this.clientSocket,myState.toString());
			break;
		case ACCEPT:
			int result = currentGame.ansEGP(this, 1);
			if(result == -1)
				outRequest = new Request(Type.MESSAGE,"waiting for player2");
			else if(result == 1)
			{
				outRequest = new Request(Type.GAMEOVER,currentGame.getWinner() + ":" + currentGame.getBlackScore() + ":" + currentGame.getWhiteScore());
				currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			}else if(result == 0){
				currentGame.restoreBoard();
				String temp = currentGame.getBoard();
				outRequest = new Request(Type.DENY,"EGP" + temp);
				currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			}
			break;
		case DENY:
			int result2 = currentGame.ansEGP(this, 0);
			if(result2 == -1)
				outRequest = new Request(Type.MESSAGE,"waiting for player2");
			else if(result2 == 0)
			{
				currentGame.restoreBoard();
				String temp = currentGame.getBoard();
				outRequest = new Request(Type.DENY,"EGP" + temp);
				currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			}
			break;
		case GAMEOVER:
			outRequest = myState.QuitGame(this,request.getValue());
			break;
		case MOVE:
			String input = request.getValue();
			int i;
			
			i=input.indexOf(':');
			int x = Integer.parseInt(input.substring(0, i));
			input = input.substring(i+1);
			
			i=input.indexOf(':');
			int y = Integer.parseInt(input.substring(0, i));
			input = input.substring(i+1);
			
			int color = Integer.parseInt(input);
			
			outRequest = currentGame.makeMove(this, x, y, color);
			break;
		case WARNING:
			break;
		case LOGIN:
			try {
				outRequest = myState.LogIn(this, request.getValue());
			} catch (NoAccountFoundException | WrongPasswordException e) {
				outRequest = new Request(Type.DENY,e.getMessage());
			}
			break;
		case MESSAGE:
			outRequest = new Request(Type.MESSAGE,request.getValue());
			this.currentGame.sendToClients(this, Protocol.getMessage(outRequest));
			break;
		case EXIT:
			outRequest = new Request(Type.EXIT,"bye");
			break;
		case REGISTER:
			try {
				outRequest = myState.Register(request.getValue());
			} catch (AccountExistsException e) {
				outRequest = new Request(Type.DENY,e.getMessage());
			}
			break;
		case LOGOUT:
			outRequest = myState.LogOut(this);
			break;
		default:
            System.out.println("Invalid message");
        break;
		
		}
		
		return outRequest;
	}
	
	public void changeState(MyState state)
	{
		this.myState = state;
	}
	
	public MyState getMyState()
	{
		return this.myState;
	}

	public void setGame(GameThread game)
	{
		this.currentGame = game;
	}
	
	public GameThread getGame()
	{
		return this.currentGame;
	}

	public Account getAccount() {
		return account;
	}
	
	public void setGameSize(int gameSize)
	{
		this.gameSize = gameSize;
	}
	
	public int getGameSize()
	{
		return this.gameSize;
	}

	public void sendToClient(String input)
	{
		out.println(input);
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	private static void log(Socket client, String message)
	{
		Server.getInstance().log(client,message);
	}
}