package exceptions;

public class WrongMoveException extends Exception {

	@Override
	public String getMessage()
	{
		return "Wrong password!";
	}
}
