package tp.project.goGame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {
	private static final int PORT = 7788;
	private static Server instance;
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static ServerGUI gui = null;
	
	private static Vector<ClientThread> clients = new Vector<ClientThread>();
	private static Vector<GameThread> games = new Vector<GameThread>();
	
	public static Server getInstance(){
		if(instance == null)
		{
			synchronized(Server.class)
			{
				if(instance == null)
					instance = new Server();
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
        try {
        	serverSocket = new ServerSocket(PORT);
        	gui = new ServerGUI();
        	
        	System.out.println("The chat server is running.");
        	
            while (true) {
                clientSocket = serverSocket.accept();
                clients.addElement(new ClientThread(clientSocket));
                clients.lastElement().start();
                log("New connection: " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeThread(ClientThread clientThread)
	{
		try {
			clientThread.join();
			clients.remove(clientThread);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void log(String message)
	{
		gui.addLog(message);
	}
	
	@SuppressWarnings("unused")
	public static void log(Socket client,String message)
	{
		gui.addLog(client.getInetAddress() + ">" + message);
	}
}
