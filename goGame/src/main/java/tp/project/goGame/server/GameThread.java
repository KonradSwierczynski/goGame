package tp.project.goGame.server;

public class GameThread {
	ClientThread player1,player2 = null;
	int gameSize;
	
	public GameThread(ClientThread player1, ClientThread player2, int gameSize)
	{
		this.player1 = player1;
		this.player2 = player2;
		this.gameSize = gameSize;
	}
	
	void sendToClients(ClientThread from,String input)
	{
		if(from.equals(player1))
			player2.sendToClient(input);
		else
			player1.sendToClient(input);
	}

}
