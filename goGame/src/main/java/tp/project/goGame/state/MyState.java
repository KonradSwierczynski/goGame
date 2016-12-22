package tp.project.goGame.state;

import exceptions.AccountExistsException;
import exceptions.NoAccountFoundException;
import exceptions.WrongPasswordException;
import tp.project.goGame.server.ClientThread;
import tp.project.goGame.server.GameThread;
import tp.project.goGame.shared.Request;

public interface MyState {
	void ChangeState(ClientThread client, MyState state);
	public Request LogIn(ClientThread client, String input) throws NoAccountFoundException, WrongPasswordException;
	public Request Register(String input) throws AccountExistsException;
	public Request LogOut(ClientThread client);
	public Request PlayGame(ClientThread client, String input);
	public Request StartGame(ClientThread client, String input);
	public Request SendMesage(String input);
	public Request MakeMove(GameThread gameThread,String input);
	public Request QuitGame(ClientThread client,String input);
	public Request QuitQueue(ClientThread client);
}