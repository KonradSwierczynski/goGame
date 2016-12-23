package tp.project.goGame.shared;

/**
 * Class for messages made to send between client and server
 *
 */
public class Request {
	private Type type;
	private String value;

	public Request(Type type, String value)
	{
		this.type = type;
		this.value = value;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value)  {
		this.value = value;
	}

}
