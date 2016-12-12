package tp.project.goGame.client.Model;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import tp.project.goGame.shared.*;

public class Model {
	
	private String serverAddress = "Localhost";
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private Account account;
	
	public Model() {
		Connect();
	}
	
	private void Connect() {
		try {
			client = new Socket(serverAddress, 7788);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			checkConnection();
		} catch (Exception e) {
			System.err.print(e.toString());
			e.printStackTrace();
		}
	}
	
	private void checkConnection() throws Exception {
		String line;
		line = in.readLine();
		if(line != null) {
			if(!(Protocol.getRequest(line).getType().equals(Type.ACCEPT) && (Protocol.getRequest(line).getValue().equals("pass")))) {
				in.close();
				out.close();
				client.close();
				throw new Exception("Failde to connect");
			}
		}
	}

	public void CreateAccount(String username, String password, String email, String nick) {
		Request request = new Request(Type.REGISTER, username + ":" + password + ":" + email + ":" +nick);
		String message = Protocol.getMessage(request);
		out.println(message);
	}
	
	public void LogIn(String username, String password) {
		Request request = new Request(Type.LOGIN, username + ":" + password + "::");
		String message = Protocol.getMessage(request);
		out.println(message);
	}
}
