package tp.project.goGame.shared;

import java.util.Random;

import exceptions.WrongMoveException;

/**
 * The Board class implements game logic and actions that can be made in game.
 * It store informations about current game and handle actions. 
 */
public class Board {
	//compensation for second player
	private final float KOMI = 6.5f;
	private float blackScore, whiteScore, blackCaptured, whiteCaptured;
	//colors which occur in game board
	public int BLACK = 1, WHITE = 2, EMPTY = 0, BLACKTERRITORY = 3, WHITETERRITORY = 4;
	private int[][] board;
	private int[][] previousBoard;
	private int[][] boardBeforeEnd;
	private GameSize gameSize;
	private int size;
	private String currentLeader = "BOT";
	
	/**
	 * Constructior creating class for scpecified game
	 * @param gameSize Size of the board in game
	 */
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
	
	/**
	 * Method making move on the board.
	 * If move is not acceptable, an exception is thrown.
	 * @param x	First coordinate of the place, where stone is going to be placed
	 * @param y Second coordinate of the place, where stone is going to be placed
	 * @param color Color of the stone
	 * @throws WrongMoveException	Thrown when the move does not meet rules
	 */
	public void makeMove(int x, int y, int color) throws WrongMoveException {
		
		if(allPositionsOccupied()) {
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
	
	/**
	 * Copies current values form one two dimensional table to second.
	 * Tables have to be in size of the game board
	 * @param to	Board to copy values to
	 * @param from	Board to copy values from
	 */
	private void boardCpy(int[][] to, int[][] from) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				to[i][j] = from[i][j];
			}
		}
	}
	
	/**
	 * Prints int values from board and previous board to console
	 */
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

	/**
	 * Checks if there are still free positions on the board
	 * @return	True if there is at least one free position, otherwise returns false 
	 */
	public boolean allPositionsOccupied() {
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
	
	/**
	 * Prepares board to give information about points and territories
	 */
	public void endGame() {
		boardCpy(boardBeforeEnd, board);
		float blackCapturedBk = this.blackCaptured;
		float whiteCapturedBk = this.whiteCaptured;
		
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
		
		
		this.blackScore += this.blackCaptured - blackCapturedBk;
		this.whiteScore += this.whiteCaptured - whiteCapturedBk;
		this.blackCaptured = blackCapturedBk;
		this.whiteCaptured = whiteCapturedBk;
		
		markTerritory();
		
		this.printBoards();
	}
	
	/**
	 * Searches for territories and marks them off
	 */
	private void markTerritory() {
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
	
	/**
	 * Sets values of the specified territory on the board
	 * @param x	First coordinate of any point in territory
	 * @param y	Second coordinate of any point in territory
	 * @param territoryColor Color which specifies this territory
	 */
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

	/**
	 * Checks if the point belongs to territory of specified color
	 * @param x	First coordinate of the point
	 * @param y Second coordinate of the point
	 * @param color Color of player which this territory might belongs to
	 * @param tab	Temporary board where visited points are marked, if calling method pass empty board
	 * @return	True if the point belongs to territory of the player
	 */
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

	/**
	 * Counts final score for the player with black stones
	 * @return	Final score for the player with black stones
	 */
	public float getBlackScore() {
		return blackScore + blackCaptured;
	}
	
	/**
	 * Counts final score for the player with white stones
	 * @return	Final score for the player with white stones
	 */
	public float getWhiteScore() {
		return whiteScore + KOMI + whiteCaptured;
	}
	
	/**
	 * Getter for number of stones captured by the player with black stones
	 * @return Number of stones captured by the player with black stones
	 */
	public float getBlackCaptured() {
		return blackCaptured;
	}
	
	/**
	 * Getter for number of stones captured by the player with white stones
	 * @return Number of stones captured by the player with white stones
	 */
	public float getWhiteCaptured() {
		return whiteCaptured;
	}

	/**
	 * Calculates color of the opponent player
	 * @param color Color of the current player
	 * @return Color off the opponent player
	 */
	private int opponentColor(int color) {
		if( color == 1 || color == 2) {
			return (2 - color/2);
		}
		return -1;
	}
	
	/**
	 * Checks if new made move made board in KO
	 * @return True if the board is on KO
	 */
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
	
	/**
	 * Updates board with changes made by new move
	 * @param x	First coordinate of the new point
	 * @param y	Second coordinate of the new point
	 * @param color Color of the new point
	 */
	private void updateBoard(int x, int y, int color) {
		tryToKill(x, y, color);
		if(!hasLiberty(x, y, color, new int[size][size])) {
			board[x][y] = 0;
		}
	}
	
	/**
	 * Checks if the point can be killed
	 * @param x	First coordinate of the point
	 * @param y	Second coordinate of the point
	 * @param color Color of the point
	 */
	private void tryToKill(int x, int y, int color) {
		int opponentColor = opponentColor(color);
		
		if(isInBoard(x - 1, y))
			if(board[x - 1][y] == opponentColor && !hasLiberty(x - 1, y, opponentColor, new int[size][size])) {
				kill(x - 1, y, opponentColor);
			}
		if(isInBoard(x, y - 1))
			if(board[x][y - 1] == opponentColor && !hasLiberty(x, y - 1, opponentColor, new int[size][size])) {
				kill(x, y - 1, opponentColor);
			}
		if(isInBoard(x + 1, y))
			if(board[x + 1][y] == opponentColor && !hasLiberty(x + 1, y, opponentColor, new int[size][size])) {
				kill(x + 1, y, opponentColor);
			}
		if(isInBoard(x, y + 1))
			if(board[x][y + 1] == opponentColor && !hasLiberty(x, y + 1, opponentColor, new int[size][size])) {
				kill(x, y + 1, opponentColor);
			}
		
	}

	/**
	 * Kills stones in the group of specified stone
	 * @param x	First coordinate of the stone from the group
	 * @param y	Second coordinate of the stone from the group
	 * @param color Color of stones in the group
	 */
	private void kill(int x, int y, int color) {
		board[x][y] = 0;
		if(color == this.BLACK)
			whiteCaptured++;
		if(color == this.WHITE)
			blackCaptured++;
		
		if(isInBoard(x - 1, y) && board[x - 1][y] == color)
			kill(x - 1, y, color);	
		if(isInBoard(x, y - 1) && board[x][y - 1] == color)
			kill(x, y - 1, color);
		if(isInBoard(x + 1, y) && board[x + 1][y] == color)
			kill(x + 1, y, color);
		if(isInBoard(x, y + 1) && board[x][y + 1] == color)
			kill(x, y + 1, color);
	}
	
	/**
	 * Checks if the group the point belongs to has at least one liberty
	 * @param x	First coordinate of the point
	 * @param y Second coordinate of the point
	 * @param color Color of the stones in group
	 * @param tab	Temporary board where visited points are marked, if calling method pass empty board
	 * @return	True if the group has at least one liberty
	 */
	private boolean hasLiberty(int x, int y, int color, int[][] tab) {
		if(board[x][y] == 0)
			return true;
		if(board[x][y] != color)
			return false;
		if(tab[x][y] == 1)
			return false;
		
		tab[x][y] = 1;
		boolean hasLibertie = false;
		
		if(isInBoard(x - 1, y))
			hasLibertie = hasLibertie || hasLiberty(x - 1, y, color, tab);
		if(isInBoard(x, y - 1))
			hasLibertie = hasLibertie || hasLiberty(x, y - 1, color, tab);
		if(isInBoard(x + 1, y))
			hasLibertie = hasLibertie || hasLiberty(x + 1, y, color, tab);
		if(isInBoard(x, y + 1))
			hasLibertie = hasLibertie || hasLiberty(x, y + 1, color, tab);
		
		return hasLibertie;
	}
	
	/**
	 * Checks if the point is in the board
	 * @param x	First coordinate of the point
	 * @param y Second coordinate of the point
	 * @return True if the point is in the board
	 */
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
	
	/**
	 * Restores board to board before ending.
	 * Used when the game is wanted to continue
	 */
	public void restoreBoard() {
		boardCpy(board, boardBeforeEnd);
		blackScore = 0f;
		whiteScore = 0f;
	}
	
	
	public String getWinner()
	{
		return currentLeader;
	}
	
	public void setWinner(String winner)
	{
		currentLeader = winner;
	}

	public void makeBotMove(int i) {
		int x,y;
		Random rng = new Random();
		while(true)
		{
			x = rng.nextInt(size);
			y = rng.nextInt(size);
			try{
				this.makeMove(x, y, i);
				break;
			}catch(WrongMoveException e)
			{
				continue;
			}
		}
		
	}
	
}
