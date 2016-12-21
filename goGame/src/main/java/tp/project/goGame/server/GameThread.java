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
	int passCount = 0;
	boolean acc1,acc2 = false;
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
	
	public void denyCase()
	{
		acc2 = acc1 = false;
	}
	
	public synchronized boolean acceptEGP(ClientThread client)
	{
		Request temp;
		if(client.equals(player1))
		{
			if(acc2)
			{
				temp = new Request(Type.GAMEOVER,board.getWinner());
				
				player1.proceedAction(Protocol.getMessage(temp));
				player2.proceedAction(Protocol.getMessage(temp));
		
				return true;
			}
			else
			{
				acc1 = true;
				return false;
			}
		}else if(client.equals(player2))
		{
			if(acc1){
				temp = new Request(Type.GAMEOVER,board.getWinner());
				player1.proceedAction(Protocol.getMessage(temp));
				player2.proceedAction(Protocol.getMessage(temp));
				return true;
			}
			else
			{
				acc2=true;
				return false;
			}
		}
		
		return false;
	}
	
	public Request makePass(ClientThread from)
	{
		passCount++;
		Request out = new Request(Type.PASS,from.getAccount().getNickname());
		if(from.equals(player1))
			player2.sendToClient(Protocol.getMessage(out));
		else
			player1.sendToClient(Protocol.getMessage(out));
		
		if(passCount==2)
		{
			Request egp = new Request(Type.ENDGAMEPROMPT,"");
			player2.sendToClient(Protocol.getMessage(egp));
			player1.sendToClient(Protocol.getMessage(egp));
		}
		
		return out;
	}
	
	public Request makeMove(ClientThread from, int x, int y,int color)
	{
		passCount = 0;
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
	
	public String getWinner()
	{
		return board.getWinner();
	}

}
