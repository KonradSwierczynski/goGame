package tp.project.goGame.shared;

import exceptions.WrongMoveException;

public class Board {
	
	public int BLACK = 1, WHITE = 2, EMPTY = 0;
	
	private int[][] board;
	private int[][] previousBoard;
	private int currentRoundColor;
	private GameSize gameSize;
	private int size;
	private String currentLeader;
	private int numberOfPassesInRow = 0;

	
	public Board(GameSize gameSize) {
		this.board = new int[gameSize.getValue()][gameSize.getValue()];
		this.previousBoard = new int[gameSize.getValue()][gameSize.getValue()];
		this.gameSize = gameSize;
		this.size = gameSize.getValue();
	}
	
	public GameSize getSize() {
		return gameSize;
	}
	
	public int getIntSize() {
		return size;
	}
	
	public void pass(int color) throws WrongMoveException {
		if(color != currentRoundColor)
			throw new WrongMoveException("Wrong player");
		if(numberOfPassesInRow == 2) {
			endGame();
		}
		currentRoundColor = opponentColor(color);
		numberOfPassesInRow++;
	}
	
	private void endGame() {
		// TODO Auto-generated method stub
		
	}

	public void makeMove(int x, int y, int color) throws WrongMoveException {
		
		if(checkFreePositions()) {
			throw new WrongMoveException("No more free positions");
		}
		
		if(!isInBoard(x, y))
			throw new WrongMoveException("Wrong position");
		if(board[x][y] != 0)
			throw new WrongMoveException("Occupied position");
		if(color != currentRoundColor)
			throw new WrongMoveException("Wrong player");
		
		int[][] tmp = board;
		board[x][y] = color;
		
		updateBoard(x, y, color);
		
		if(board[x][y] != color) {
			board = tmp;
			throw new WrongMoveException("Sueside is not allowed");
		}
		
		if(isKo()) {
			board = tmp;
			throw new WrongMoveException("Wrong move - KO");
		}
		
		previousBoard = tmp;
		currentRoundColor = opponentColor(color);
		numberOfPassesInRow = 0;
		
	}
	
	private boolean checkFreePositions() {
		boolean noFreePositions = true;
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(board[i][j] == 0) {
					noFreePositions = false;
				}
			}
		}
		
		return noFreePositions;
	}

	private int opponentColor(int color) {
		if( color == 1 || color == 2) {
			return (2 - color/2);
		}
		return -1;
	}
	
	private boolean isKo() {
		boolean isKo = true;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(board[i][j] != previousBoard[i][j]) {
					isKo = false;
				}
			}
		}
		
		return isKo;
	}
	
	private void updateBoard(int x, int y, int color) {
		tryToKill(x, y, color);
		if(!hasLibertie(x, y, color, new int[size][size])) {
			board[x][y] = 0;
		}
	}
	
	private void tryToKill(int x, int y, int color) {
		int opponentColor = opponentColor(color);
		
		if(isInBoard(x - 1, y))
			if(board[x - 1][y] == opponentColor && !hasLibertie(x - 1, y, opponentColor, new int[size][size])) {
				kill(x - 1, y, opponentColor);
			}
		if(isInBoard(x, y - 1))
			if(board[x][y - 1] == opponentColor && !hasLibertie(x, y - 1, opponentColor, new int[size][size])) {
				kill(x, y - 1, opponentColor);
			}
		if(isInBoard(x + 1, y))
			if(board[x + 1][y] == opponentColor && !hasLibertie(x + 1, y, opponentColor, new int[size][size])) {
				kill(x + 1, y, opponentColor);
			}
		if(isInBoard(x, y + 1))
			if(board[x][y + 1] == opponentColor && !hasLibertie(x, y + 1, opponentColor, new int[size][size])) {
				kill(x, y + 1, opponentColor);
			}
		
	}

	private void kill(int x, int y, int color) {
		board[x][y] = 0;
		
		if(isInBoard(x - 1, y) && board[x - 1][y] == color)
			kill(x - 1, y, color);	
		if(isInBoard(x, y - 1) && board[x][y - 1] == color)
			kill(x, y - 1, color);
		if(isInBoard(x + 1, y) && board[x + 1][y] == color)
			kill(x + 1, y, color);
		if(isInBoard(x, y + 1) && board[x][y + 1] == color)
			kill(x, y + 1, color);
	}
	
	private boolean hasLibertie(int x, int y, int color, int[][] tab) {
		if(board[x][y] == 0)
			return true;
		if(board[x][y] != color)
			return false;
		if(tab[x][y] == 1)
			return false;
		
		tab[x][y] = 1;
		boolean hasLibertie = false;
		
		if(isInBoard(x - 1, y))
			hasLibertie = hasLibertie || hasLibertie(x - 1, y, color, tab);
		if(isInBoard(x, y - 1))
			hasLibertie = hasLibertie || hasLibertie(x, y - 1, color, tab);
		if(isInBoard(x + 1, y))
			hasLibertie = hasLibertie || hasLibertie(x + 1, y, color, tab);
		if(isInBoard(x, y + 1))
			hasLibertie = hasLibertie || hasLibertie(x, y + 1, color, tab);
		
		return hasLibertie;
	}
	
	private boolean isInBoard(int x, int y) {
		return 0 <= x && x < size && 0 <= y && y < size;
	}

	public String getBoard() {
		String boardString = "";
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				boardString += Integer.toString(board[i][j]) + ":";
			}
			boardString += ":";
		}
		
		return boardString;
	}
	
	public void restoreBoard(String boardString) {
		int position = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				board[i][j] = Character.getNumericValue(boardString.charAt(position));
				position += 2;
			}
			position += 1;
		}
	}
	
	public String getWinner()
	{
		return currentLeader;
	}
	
}
