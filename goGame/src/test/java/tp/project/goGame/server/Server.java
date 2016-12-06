package tp.project.goGame.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static Server instance;
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	
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
		// TODO Auto-generated method stub

	}

}
