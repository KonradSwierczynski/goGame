package tp.project.goGame.server;

import exceptions.WrongMoveException;
import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.GameSize;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

public class GameThread {
	ClientThread player1,player2 = null;
	int gameSize;
	Board board = null;
	/*
	 * player1 is 1(black), player2 is 2(white)
	 */
	public GameThread(ClientThread player1, ClientThread player2, int gameSize)
	{
		this.player1 = player1;
		this.player2 = player2;
		this.gameSize = gameSize;
		switch(gameSize)
		{
		case 19:
			board = new Board(GameSize.size19x19);
			break;
		case 13:
			board = new Board(GameSize.size13x13);
			break;
		case 9:
			board = new Board(GameSize.size9x9);
			break;
		}
	}
	
	public Request makePass(ClientThread from)
	{
		Request out = new Request(Type.PASS,"");
		if(from.equals(player1))
			player2.sendToClient(Protocol.getMessage(out));
		else
			player1.sendToClient(Protocol.getMessage(out));
		
		return out;
	}
	
	public Request makeMove(ClientThread from, int x, int y,int color)
	{
		Request out;
		try{
			if(from.equals(player1))
			{
				board.makeMove(x, y, color);
				out = new Request(Type.MOVE,"MOV"+board.getBoard());
				player2.sendToClient(Protocol.getMessage(out));
			} else {
				board.makeMove(x, y, color);
				out = new Request(Type.MOVE,"MOV"+board.getBoard());
				player1.sendToClient(Protocol.getMessage(out));
			}
			
			return out;
			
		}catch(WrongMoveException e)
		{
			return new Request(Type.DENY,"Wrong move!");
		}
		
		
	}
	
	void sendToClients(ClientThread from,String input)
	{
		if(from.equals(player1))
			player2.sendToClient(input);
		else
			player1.sendToClient(input);
	}

}
