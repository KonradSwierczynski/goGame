package tp.project.goGame.state;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Request;

public class InGameState implements MyState{

	@Override
	public void ChangeState(ClientThread client, MyState state) {
		// TODO Auto-generated method stub
		
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
	public Request QuitGame(ClientThread client) {
		// TODO Auto-generated method stub
		return null;
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
