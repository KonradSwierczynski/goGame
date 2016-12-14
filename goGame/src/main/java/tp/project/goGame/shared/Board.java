package tp.project.goGame.shared;

import exceptions.WrongMoveException;

public class Board {
	
	public int BLACK = 1, WHITE = 2, EMPTY = 0;
	
	private int[][] board;
	private int size;
	
	public Board(int size) {
		//TODO size should be from enum Size (switch here)
		board = new int[size][size];
		this.size = size;
	}
	
	public int getSize() {
		//TODO should return enum Size
		return size;
	}
	
	public void makeMove(int x, int y) throws WrongMoveException {
		
	}
	
	@Override
	public String toString() {
		return null;	
	}

}
