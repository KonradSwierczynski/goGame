import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tp.project.goGame.shared.Account;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

public class ProtocolTest {

	@Test
	public void testGetRequest() {
		Request rq = new Request(Type.CONCEDE,"hello");
		assertEquals(Protocol.getMessage(rq),"CNChello");
	}
	
	@Test
	public void testGetMessage() {
		String msg = "EGPtralala";
		Request temp = Protocol.getRequest(msg);
		Request temp2 = new Request(Type.ENDGAMEPROMPT,"tralala");
		assertEquals(temp.getValue(),temp2.getValue());
	}
	
	@Test
	public void testGetAccount()
	{
		String line = "bartek:gralek:mdasd:13eds";
		Account temp = Protocol.getAccount(line);
		assertEquals(temp.getEmail(),"mdasd");
	}

}
