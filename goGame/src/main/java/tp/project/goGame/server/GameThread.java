package tp.project.goGame.server;

import exceptions.WrongMoveException;
import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.GameSize;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;
import tp.project.goGame.state.LoggedInState;

public class GameThread {
	ClientThread player1,player2 = null;
	int gameSize;
	Board board = null;
	int passCount = 0;
	int denyCount = 0;
	int accCount = 0;
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
	
	public synchronized int ansEGP(ClientThread from,int value)
	{
		//1 - accept
		//0 - deny
		switch(value)
		{
		case 1:
			accCount++;
			break;
		case 0:
			denyCount++;
			break;
		}
		
		if((denyCount+accCount)==2)
		{
			if(denyCount==2)
			{
				denyCount = 0;
				accCount = 0;
				return 0;
			}
			else if(accCount==2)
			{
				//TODO DATABASE CONNECTION
				board.endGame();
				if(board.getBlackScore()>board.getWhiteScore())
				{
					board.setWinner(player1.getAccount().getNickname());
				}else if(board.getBlackScore()<board.getWhiteScore())
				{
					board.setWinner(player2.getAccount().getNickname());
				}
				else
					board.setWinner("none");
				
				denyCount = 0;
				accCount = 0;
				player1.changeState(new LoggedInState());
				player2.changeState(new LoggedInState());
				return 1;
			}
			else
			{
				//TODO
				denyCount = 0;
				accCount = 0;
				return 0;
			}
			
		
		}else
		return -1;
		
	}

	public Request makePass(ClientThread from)
	{
		passCount++;
		Request out = null;
		if(passCount==1)
		{
			out = new Request(Type.PASS,from.getAccount().getNickname());
			if(from.equals(player1))
				player2.sendToClient(Protocol.getMessage(out));
			else
				player1.sendToClient(Protocol.getMessage(out));
		}
		else if(passCount==2)
		{
			board.endGame();
			out = new Request(Type.ENDGAMEPROMPT,board.getBoard());
			if(from.equals(player1))
				player2.sendToClient(Protocol.getMessage(out));
			else
				player1.sendToClient(Protocol.getMessage(out));
		}
		
		return out;
	}
	
	public Request makeMove(ClientThread from, int x, int y,int color)
	{
		
		Request out;
		try{
			if(from.equals(player1))
			{
				board.makeMove(x, y, color);
				out = new Request(Type.MOVE,board.getBoard());
				player2.sendToClient(Protocol.getMessage(out));
			} else {
				board.makeMove(x, y, color);
				out = new Request(Type.MOVE,board.getBoard());
				player1.sendToClient(Protocol.getMessage(out));
			}
			passCount = 0;
			return out;
			
		}catch(WrongMoveException e)
		{
			return new Request(Type.DENY,e.getMessage());
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
	
	public float getWhiteScore()
	{
		return board.getWhiteScore();
	}
	
	public float getBlackScore()
	{
		return board.getBlackScore();
	}
	
	public void restoreBoard()
	{
		board.restoreBoard();
	}
	
	public String getBoard()
	{
		return board.getBoard();
	}

}
