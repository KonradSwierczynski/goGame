package tp.project.goGame.shared;

import exceptions.WrongMoveException;

public class Board {
	
	private final float KOMI = 6.5f;
	private float blackScore, whiteScore, blackCaptured, whiteCaptured;
	
	public int BLACK = 1, WHITE = 2, EMPTY = 0, BLACKTERRITORY = 3, WHITETERRITORY = 4;
	private int[][] board;
	private int[][] previousBoard;
	private int[][] boardBeforeEnd;
	private GameSize gameSize;
	private int size;
	private String currentLeader = "Bartol";
	
	public Board(GameSize gameSize) {
		this.board = new int[gameSize.getValue()][gameSize.getValue()];
		this.previousBoard = new int[gameSize.getValue()][gameSize.getValue()];
		this.boardBeforeEnd = new int[gameSize.getValue()][gameSize.getValue()];
		this.gameSize = gameSize;
		this.size = gameSize.getValue();
	}
	
	public GameSize getSize() {
		return gameSize;
	}
	
	public int getIntSize() {
		return size;
	}
	
	public void makeMove(int x, int y, int color) throws WrongMoveException {
		
		if(checkFreePositions()) {
			throw new WrongMoveException("No more free positions");
		}
		
		if(!isInBoard(x, y))
			throw new WrongMoveException("Wrong position");
		if(board[x][y] != 0)
			throw new WrongMoveException("Occupied position");
		
		int[][] tmp = new int[size][size];
		boardCpy(tmp, board);
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
		
		boardCpy(previousBoard, tmp);
		printBoards();
	}
	
	private void boardCpy(int[][] to, int[][] from) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				to[i][j] = from[i][j];
			}
		}
	}
	
	private void printBoards() {
		System.out.println("Board");
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
		System.out.println("Previous Board");
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				System.out.print(this.previousBoard[i][j]);
			}
			System.out.println("");
		}
	}

	public boolean checkFreePositions() {
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
	
	public void endGame() {
		boardCpy(boardBeforeEnd, board);
		
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(board[i][j] == 0) {
					try {
						makeMove(i, j, 1);
					} catch (WrongMoveException e) {
					}
					
					board[i][j] = 0;
					try {
						makeMove(i, j, 2);
					} catch (WrongMoveException e) {
					}
					board[i][j] = 0;
				}
			}
		}
		
		countTerritory();
		
		this.printBoards();
	}
	
	private void countTerritory() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(board[i][j] == 0) {
					if(isTerritory(i, j, this.BLACK, new int[size][size])) {
						setTerritory(i, j, this.BLACKTERRITORY);
					} else if(isTerritory(i, j, this.WHITE, new int[size][size])) {
						setTerritory(i, j, this.WHITETERRITORY);
					}		
				}
			}
		}
	}
	
	
	private void setTerritory(int x, int y, int territoryColor) {
		board[x][y] = territoryColor;
		if(territoryColor == BLACKTERRITORY)
			blackScore++;
		if(territoryColor == WHITETERRITORY)
			whiteScore++;
		
		if(isInBoard(x - 1, y) && board[x - 1][y] == 0)
			setTerritory(x - 1, y, territoryColor);	
		if(isInBoard(x, y - 1) && board[x][y - 1] == 0)
			setTerritory(x, y - 1, territoryColor);
		if(isInBoard(x + 1, y) && board[x + 1][y] == 0)
			setTerritory(x + 1, y, territoryColor);
		if(isInBoard(x, y + 1) && board[x][y + 1] == 0)
			setTerritory(x, y + 1, territoryColor);	
	}

	private boolean isTerritory(int x, int y, int color, int[][] tab) {
		if(board[x][y] == color)
			return true;
		if(board[x][y] == opponentColor(color) || board[x][y] > 2)
			return false;
		
		tab[x][y] = 1;
		boolean isTerritory = true;
		
		if(isInBoard(x - 1, y) && tab[x - 1][y] == 0)
			isTerritory = isTerritory && isTerritory(x - 1, y, color, tab);
		if(isInBoard(x, y - 1) && tab[x][y - 1] == 0)
			isTerritory = isTerritory && isTerritory(x, y - 1, color, tab);
		if(isInBoard(x + 1, y) && tab[x + 1][y] == 0)
			isTerritory = isTerritory && isTerritory(x + 1, y, color, tab);
		if(isInBoard(x, y + 1) && tab[x][y + 1] == 0)
			isTerritory = isTerritory && isTerritory(x, y + 1, color, tab);
		
		return isTerritory;

	}

	public float getBlackScore() {
		return blackScore + KOMI + blackCaptured;
	}
	
	public float getWhiteScore() {
		return whiteScore + whiteCaptured;
	}
	
	public float getBlackCaptured() {
		return blackCaptured;
	}
	
	public float getWhiteCaptured() {
		return whiteCaptured;
	}

	private int opponentColor(int color) {
		if( color == 1 || color == 2) {
			return (2 - color/2);
		}
		return -1;
	}
	
	private boolean isKo() {
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(board[i][j] != previousBoard[i][j]) {
					return false;
				}
			}
		}
		
		return true;
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
		if(color == this.BLACK)
			blackCaptured++;
		if(color == this.WHITE)
			whiteCaptured++;
		
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
	
	public void restoreBoard() {
		boardCpy(board, boardBeforeEnd);
	}
	
	public String getWinner()
	{
		return currentLeader;
	}
	
	public void setWinner(String winner)
	{
		currentLeader = winner;
	}
	
}
