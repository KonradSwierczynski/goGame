import static org.junit.Assert.*;

import org.junit.Test;

import tp.project.goGame.server.GameThread;

public class GameThreadTest {

	@Test
	public void testWinnerBot() {
		GameThread gt = new GameThread(null,null,19);
		assertEquals(gt.getWinner(),"BOT");
	}

}
