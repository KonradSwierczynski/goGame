package tp.project.goGame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;

/**
 * 
 * @author Bartosz Grałek and Konrad Świerczyński
 *
 */
public class Server {
	private static final int PORT = 7788;
	private static Server instance;
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static ServerGUI gui = null;
	
	/*
	 * Holds vector of connected Clients
	 */
	private static Vector<ClientThread> clients = new Vector<ClientThread>();
	/*
	 * Holds vector of connected GameThreads
	 */
	private static Vector<GameThread> games = new Vector<GameThread>();
	
	/*
	 * There is only one server
	 */
	public synchronized static Server getInstance(){
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
	
	/**
	 * closes given client, in order to free memory
	 * @param clientThread
	 */
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
	/**
	 * This method matches clients and creates new game for them
	 * @param new_one
	 * @param gameSize
	 */
	public void checkQueue(ClientThread new_one,int gameSize)
	{
		for(ClientThread ct: clients)
		{
			if(!new_one.equals(ct) && ct.getMyState().toString().equals("InQueueState") && ct.getGameSize() == new_one.getGameSize())
			{
				GameThread game = new GameThread(new_one,ct,ct.getGameSize());
				
				Request temp = ct.proceedAction("SGM"+ct.getGameSize()+":"+new_one.getAccount().getNickname()+":"+"2");
				ct.sendToClient(Protocol.getMessage(temp));
				
				temp = new_one.proceedAction("SGM"+ct.getGameSize()+":"+ct.getAccount().getNickname()+":"+"1");
				new_one.sendToClient(Protocol.getMessage(temp));
				
				new_one.setGame(game);
				ct.setGame(game);
				
				
				
				break;
			}
		}
	}
	
	/**
	 * Simple log
	 * @param message
	 */
	@SuppressWarnings("unused")
	public static void log(String message)
	{
		if(message.equals(null))
			gui.addLog("Null message");
		else
			gui.addLog(message);
	}
	
	@SuppressWarnings("unused")
	public static void log(Socket client,String message)
	{
		gui.addLog(client.getInetAddress() + ">" + message);
	}
}
