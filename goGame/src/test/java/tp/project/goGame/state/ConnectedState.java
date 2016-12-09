package tp.project.goGame.state;

import tp.project.goGame.request.Request;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;

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
		
		return null;
	}

	public Request Register(String input) {
		// TODO Auto-generated method stub
		return null;
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
