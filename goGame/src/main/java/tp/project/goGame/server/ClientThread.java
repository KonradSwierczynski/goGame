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
				
				log(clientSocket, Protocol.getRequest(line).getType() + ": " + Protocol.getRequest(line).getValue());
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
	Request proceedAction(String line)
	{
		Request request = Protocol.getRequest(line);
		Request outRequest = null;
		switch(request.getType())
		{
		case LEAVEQUEUE:
			outRequest = myState.QuitQueue(this);
			log(this.clientSocket,myState.toString());
			break;
		case PASS:
			
			break;
		case NEWGAME:
			outRequest = myState.PlayGame(this, request.getValue());
			log(this.clientSocket,myState.toString());
			break;
		case ACCEPT:
			break;
		case DENY:
			break;
		case GAMEOVER:
			break;
		case MOVE:
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


	public void setAccount(Account account) {
		this.account = account;
	}
	
	private static void log(Socket client, String message)
	{
		Server.getInstance().log(client,message);
	}
}