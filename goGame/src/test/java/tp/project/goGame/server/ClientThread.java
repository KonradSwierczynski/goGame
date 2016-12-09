package tp.project.goGame.server;

import tp.project.goGame.state.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import tp.project.goGame.request.Request;

public class ClientThread extends Thread{
	private BufferedReader in = null;
	private PrintWriter out = null;
	private Socket clientSocket = null;
	private Account account = null;
	private MyState myState = null;
	private int gameSize;
	
	public void run(){
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			out.println("Welcome to the server!\n");
			while(true)
			{
				String line;
				line = in.readLine();
				out.println(Protocol.getMessage(proceedAction(line)));
			}
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
		switch(request.getType())
		{
		case LOGIN:
			request = myState.LogIn(this, request.getValue());
		break;
		default:
            System.out.println("Invalid message");
        break;
		}
		
		return request;
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


	public void setAccount(Account account) {
		this.account = account;
	}
}