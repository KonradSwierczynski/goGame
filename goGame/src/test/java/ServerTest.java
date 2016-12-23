import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.WrongMoveException;
import tp.project.goGame.server.Server;

public class ServerTest {

	@Test
	public void testServerNotNull() {
		assertNotNull(Server.getInstance());
	}
	
	@Test (expected = Exception.class)
	public void testCloseThread() {
		Server.getInstance().closeThread(null);
	}

}
