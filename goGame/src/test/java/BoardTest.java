import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.WrongMoveException;
import tp.project.goGame.shared.Board;
import tp.project.goGame.shared.GameSize;

public class BoardTest {

	@Test
	public void testConstructor9() {
		Board board = new Board(GameSize.size9x9);
		assertNotNull(board);
	}
	
	@Test
	public void testConstructor13() {
		Board board = new Board(GameSize.size13x13);
		assertNotNull(board);
	}
	
	@Test
	public void testConstructor19() {
		Board board = new Board(GameSize.size19x19);
		assertNotNull(board);
	}
	
	@Test
	public void testGetSize() {
		Board board = new Board(GameSize.size9x9);
		assertTrue(board.getSize() == GameSize.size9x9);
	}
	
	@Test
	public void testIntGetSize() {
		Board board = new Board(GameSize.size9x9);
		assertTrue(board.getIntSize() == GameSize.size9x9.getValue());
	}
	
	@Test
	public void testMakeMove() {
		Board board = new Board(GameSize.size9x9);
		
		try {
			board.makeMove(0, 0, 1);
		} catch (WrongMoveException e) {
			fail();
		}
	}
	
	@Test (expected = WrongMoveException.class)
	public void testMakeMoveShouldThrowWrongMoveExceptionOccupiedPosition() throws WrongMoveException {
		Board board = new Board(GameSize.size9x9);
		board.makeMove(0, 0, 1);
		board.makeMove(0, 0, 2);
	}
	
	@Test (expected = WrongMoveException.class)
	public void testMakeMoveShouldThrowWrongMoveExceptionWrongPosition() throws WrongMoveException {
		Board board = new Board(GameSize.size9x9);
		board.makeMove(-1, 0, 1);
	}
	
	@Test
	public void testCheckFreePositions() {
		Board board = new Board(GameSize.size9x9);
		
		assertTrue(!board.allPositionsOccupied());
	}
	
	@Test
	public void testGetBlackScore() {
		Board board = new Board(GameSize.size9x9);
		
		assertTrue(board.getBlackScore() == 0);
	}
	
	@Test
	public void testGetWhiteScore() {
		Board board = new Board(GameSize.size9x9);
		
		assertTrue(board.getWhiteScore() == 6.5f);
	}
	
	@Test
	public void testGetBlackCaptured() {
		Board board = new Board(GameSize.size9x9);
		
		assertTrue(board.getBlackCaptured() == 0);
	}
	
	@Test
	public void testGetWhiteCaptured() {
		Board board = new Board(GameSize.size9x9);
		
		assertTrue(board.getWhiteCaptured() == 0);
	}
	
	@Test
	public void testRestoreBoard() {
		Board board = new Board(GameSize.size9x9);
		board.restoreBoard();
		assertNotNull(board);
	}
	
	@Test
	public void testEndGame() {
		Board board = new Board(GameSize.size9x9);
		board.endGame();
		assertNotNull(board);
	}
	
	@Test
	public void testGetBoard() {
		Board board = new Board(GameSize.size9x9);
		String boardString = board.getBoard();
		String expected = "";
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				expected += Integer.toString(0) + ":";
			}
			expected += ":";
		}
		
		assertEquals(boardString, expected);
	}

	@Test
	public void testWinner() {
		Board board = new Board(GameSize.size9x9);
		board.setWinner("test");

		assertEquals("test", board.getWinner());
	}
}
