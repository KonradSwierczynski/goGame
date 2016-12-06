package tp.project.goGame.state;

import tp.project.goGame.request.Request;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;

public interface MyState {
	void ChangeState(ClientThread client, MyState state);
	public Request LogIn(ClientThread client, String input);
	public Request Register(String input);
	public Request LogOut(ClientThread client);
	public Request PlayGame(ClientThread client, String input);
	public Request SendMesage(String input);
	public Request MakeMove(GameThread gameThread,String input);
	public Request QuitGame(ClientThread client);
	public Request QuitQueue(ClientThread client);
}
