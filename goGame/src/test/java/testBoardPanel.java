import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import tp.project.goGame.client.BoardPanel;

public class testBoardPanel {

	@Test
	public void test() {
		BoardPanel bp = new BoardPanel(3);
		assertNotNull(bp);
	}

}
