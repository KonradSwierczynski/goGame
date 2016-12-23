package tp.project.goGame.shared;

/**
 * This is a Request type
 * it holds type and value
 * @author Bartek
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
