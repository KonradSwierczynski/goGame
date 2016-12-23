package tp.project.goGame.shared;

/**
 * Enum for possible sizes of board in the game
 *
 */
public enum GameSize {
	size9x9 (9),
	size13x13 (13),
	size19x19 (19);
	
	private final int size;
	private GameSize(int size) { this.size = size; }
	public int getValue() { return size; }
}
