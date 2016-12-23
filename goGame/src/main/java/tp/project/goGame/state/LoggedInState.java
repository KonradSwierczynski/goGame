package tp.project.goGame.state;

import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Request;
import tp.project.goGame.shared.Type;

/**
 * State for logged user
 *
 */
public class LoggedInState implements MyState {

	
	public void ChangeState(ClientThread client, MyState state) {
		client.changeState(state);
	}

	public Request LogIn(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request Register(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public Request LogOut(ClientThread client) {
		client.setAccount(null);
		client.changeState(new ConnectedState());
		return new Request(Type.LOGOUT,"bye");
	}

	public Request PlayGame(ClientThread client, String input) {
		Request out = null;
		
		String line = input;
		String mode = input.substring(0, 3);
		int size = Integer.parseInt(input.substring(3));
		client.setGameSize(size);
		if(mode.equals("pvp"))
		{
			out = new Request(Type.NEWGAME,input);
			client.changeState(new InQueueState());	
			
		}else if(mode.equals("bot"))
		{
			GameThread gameThread = new GameThread(client,null,size);
			client.setGame(gameThread);
			client.changeState(new InGameState());
			out = new Request(Type.STARTGAME,size+":BOT:1");
		}
		
		return out;
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
		return "LoggedInstate";
	}

	@Override
	public Request StartGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Request QuitGame(ClientThread client, String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
