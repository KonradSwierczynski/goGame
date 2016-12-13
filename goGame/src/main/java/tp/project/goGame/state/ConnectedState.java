package tp.project.goGame.state;

import java.util.List;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Account;
import tp.project.goGame.shared.Protocol;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

public class ConnectedState implements MyState {
	private static MyState instance;
	


	public void ChangeState(ClientThread client,MyState state) {
		client.changeState(state);
	}

	public Request LogIn(ClientThread client, String input) throws NoAccountFoundException, WrongPasswordException {
		Account logAccount = Protocol.getAccount(input);
		List<Account> accs = DataBaseConnector.getInstance().read();
		Account out = null;
		for(Account a : accs)
		{
			if(a.getUsername().equals(logAccount.getUsername()) && a.getPassword().equals(logAccount.getPassword()))
			{
				out = a;
				break;
			}
			
			if(a.getUsername().equals(logAccount.getUsername()) && !a.getPassword().equals(logAccount.getPassword()))
			{
				throw new WrongPasswordException();
			}
		}
		
		if(out == null)
		{
			throw new NoAccountFoundException(logAccount.getUsername());
		}
		
		client.setAccount(out);
		client.changeState(new LoggedInState());
		return new Request(Type.LOGIN,out.getNickname());
	}

	public Request Register(String input) throws AccountExistsException {
		Account newAccount = Protocol.getAccount(input);
		DataBaseConnector dbc = DataBaseConnector.getInstance();
		dbc.create(newAccount);
		return new Request(Type.REGISTER,"ok");
	}

	public Request LogOut(ClientThread client) {
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
	
	@Override
	public String toString()
	{
		return "ConnectedState";
	}

}
