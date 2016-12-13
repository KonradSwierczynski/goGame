package exceptions;

public class NoAccountFoundException extends Exception {
	String name;
	public NoAccountFoundException(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getMessage()
	{
		return "User: " + name + " doesn't exists";
	}

}
