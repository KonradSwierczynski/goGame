package tp.project.goGame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;

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
	
	public void checkQueue(ClientThread new_one,int gameSize)
	{
		for(ClientThread ct: clients)
		{
			if(!new_one.equals(ct) && ct.getMyState().toString().equals("InQueueState") && ct.getGameSize() == new_one.getGameSize())
			{
				GameThread game = new GameThread(new_one,ct,ct.getGameSize());
				
				Request temp = ct.proceedAction("SGM"+ct.getGameSize()+":"+new_one.getAccount().getNickname()+":"+"1");
				ct.sendToClient(Protocol.getMessage(temp));
				
				temp = new_one.proceedAction("SGM"+ct.getGameSize()+":"+ct.getAccount().getNickname()+":"+"2");
				new_one.sendToClient(Protocol.getMessage(temp));
				
				new_one.setGame(game);
				ct.setGame(game);
				
				break;
			}
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
