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

	public static Socket getClientSocket() {
		return clientSocket;
	}

	public static void setClientSocket(Socket clientSocket) {
		Server.clientSocket = clientSocket;
	}

	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		Server.serverSocket = serverSocket;
	}

}
