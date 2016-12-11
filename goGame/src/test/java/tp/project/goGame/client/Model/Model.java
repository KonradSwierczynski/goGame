package tp.project.goGame.client.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import tp.project.goGame.shared.*;

public class Model {
	
	private String serverAddress = "Localhost";
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Account account;
	
	public Model() {
		Connect();
	}
	
	private void Connect() {
		try {
			socket = new Socket(serverAddress, 5000);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(in.readLine());
		} catch (Exception e) {
			System.err.print(e.toString());
			e.printStackTrace();
		}
	}
	
	public void CreateAccount(Account account) {
		Request request = new Request(Type.REGISTER, account.toString());
		String message = Protocol.getMessage(request);
		out.println(message);
	}
	
	public void LogIn(String username, String password) {
		Request reguest = Protocol.getRequest("LogIn:" + username + ", " + password);
		String message = Protocol.getMessage(reguest);
		out.println(message);
	}

}
