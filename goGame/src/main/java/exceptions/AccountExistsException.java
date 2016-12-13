package exceptions;

public class AccountExistsException extends Exception {
	String badOne;
	
	public AccountExistsException(String badOne)
	{
		this.badOne = badOne;
	}
	
	public String getMessage()
	{
		return badOne + " already exists!";
	}
}
