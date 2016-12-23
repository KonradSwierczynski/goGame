package tp.project.goGame.state;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

/**
 * State for user in game
 *
 */
public class InGameState implements MyState{

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
	public Request QuitGame(ClientThread client, String input) {
		//database win lose;
		
		client.changeState(new LoggedInState());
		return new Request(Type.GAMEOVER,input);
	}

	@Override
	public Request QuitQueue(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString()
	{
		return "InGameState";
	}

	@Override
	public Request StartGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
