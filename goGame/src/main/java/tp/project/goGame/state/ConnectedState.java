package tp.project.goGame.state;

import java.util.List;

import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Account;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

public class ConnectedState implements MyState {
	private static MyState instance;
	
	public static MyState getInstance(){
		if(instance == null)
		{
			synchronized(ConnectedState.class)
			{
				if(instance == null)
					instance = new ConnectedState();
			}
		}
		return instance;
	}

	public void ChangeState(ClientThread client,MyState state) {
		client.changeState(state);
	}

	public Request LogIn(ClientThread client, String input) {
		Account logAccount = Protocol.getAccount(input);
		List<Account> accs = DataBaseConnector.getInstance().read();
		for(Account a : accs)
		{
			if(a.getUsername().equals(logAccount.getUsername()))
			{
				logAccount = a;
				break;
			}
		}
		
		client.setAccount(logAccount);
		client.changeState(new LoggedInState());
		return new Request(Type.ACCEPT,"pass");
	}

	public Request Register(String input) {
		Account newAccount = Protocol.getAccount(input);
		DataBaseConnector dbc = DataBaseConnector.getInstance();
		dbc.create(newAccount);
		return new Request(Type.ACCEPT,"okey");
	}

	public Request LogOut(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request playGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request PlayGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request SendMesage(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request MakeMove(GameThread gameThread, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request QuitGame(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request QuitQueue(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
	}

}
