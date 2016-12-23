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
	/**
	 * Depending on game size creates board with given size.
	 * @param player1 player1 - black
	 * @param player2 player2 - white, if it's null then it means that it's bot
	 * @param gameSize game size of this game
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
	
	/**
	 * This function returns value of End Game Prompt.
	 * @param from client, who answers for End Game Prompt
	 * @param value 1-accept 0-deny
	 * @return 0-draw/deny,1-accept,-1-cannot be said(second client didn't answer)
	 */
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
				this.QuitGamePlayers();
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

	/**
	 * Handles pass event
	 * @param from client, who calls for pass
	 * @return move with board, when playing with bot, Pass when 1 pass in a row, End Game Prompt when 2 in a row
	 *
	 */
	public Request makePass(ClientThread from)
	{
		passCount++;
		Request out = null;
		if(player2 == null)
		{
			board.makeBotMove(2);
			out = new Request(Type.MOVE,board.getBoard());
		}else
		{
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
				passCount = 0;
			}
		}
		
		return out;
	}
	
	/**
	 * This function handles make move from client
	 * @param from client, who calls for move
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param color color (1/2)
	 * @return Move request and board, when ok. Deny when wrong move.
	 */
	public Request makeMove(ClientThread from, int x, int y,int color)
	{
		
		Request out;
		try{
			if(player2!=null)
			{
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
			}
			else{
				board.makeMove(x, y, color);
				board.makeBotMove(2);
				out = new Request(Type.MOVE,board.getBoard());
				return out;
			}
				
		}catch(WrongMoveException e)
		{
			return new Request(Type.DENY,e.getMessage());
		}
		
		
	}
	
	/**
	 * Sets players State to loggedIn when game ends.
	 */
	public void QuitGamePlayers()
	{
		player1.changeState(new LoggedInState());
		player2.changeState(new LoggedInState());
	}
	
	/**
	 * Sends message to second player
	 * @param from client, who sent message
	 * @param input message
	 */
	void sendToClients(ClientThread from,String input)
	{
		if(from.equals(player1) && player2 != null)
			player2.sendToClient(input);
		else if(from.equals(player2) && player1 != null)
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
	
	public String getOpponentNickname(ClientThread client)
	{
		if(client.equals(player1))
		{
			return player2.getAccount().getNickname();
		}else
			return player1.getAccount().getNickname();
	}

}
