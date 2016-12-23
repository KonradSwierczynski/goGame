package tp.project.goGame.state;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

/**
 * State for user waiting in queue
 *
 */
public class InQueueState implements MyState {

	@Override
	public void ChangeState(ClientThread client, MyState state) {
		client.changeState(state);
		
	}

	@Override
	public Request LogIn(ClientThread client, String input) throws NoAccountFoundException, WrongPasswordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request Register(String input) throws AccountExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request LogOut(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request PlayGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request SendMesage(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request MakeMove(GameThread gameThread, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request QuitQueue(ClientThread client) {
		Request temp = new Request(Type.LEAVEQUEUE,"leave");
		client.changeState(new LoggedInState());
		return temp;
	}
	
	@Override
	public String toString()
	{
		return "InQueueState";
	}

	@Override
	public Request StartGame(ClientThread client, String input) {
		int i;
		String input2 = input;
		i=input.indexOf(':');
		int size = Integer.parseInt(input.substring(0, i));
		client.setGameSize(size);
		
		input = input.substring(i+1);
		
		i=input.indexOf(':');
		String nicknameOpponent = input.substring(0, i);
		input = input.substring(i+1);
		
		int color = Integer.parseInt(input);
		
		client.changeState(new InGameState());
		
		return new Request(Type.STARTGAME,input2);
	}

	@Override
	public Request QuitGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
