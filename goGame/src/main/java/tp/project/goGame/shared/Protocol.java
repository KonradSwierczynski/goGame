package tp.project.goGame.shared;

public class Protocol {
	public static Request getRequest(String value)
	{
		String str = value.substring(0, 3);
		Request out = null;
		switch(str)
		{
		case "MSG":
			out = new Request(Type.MESSAGE,value.substring(3));
			break;
		case "EXT":
			out = new Request(Type.EXIT,value.substring(3));
			break;
		case "ACC":
			out = new Request(Type.ACCEPT,value.substring(3));
			break;
		case "DNY":
			out = new Request(Type.DENY,value.substring(3));
			break;
		case "GMO":
			out = new Request(Type.GAMEOVER,value.substring(3));
			break;
		case "LOG":
			out = new Request(Type.LOGIN,value.substring(3));
			break;
		case "MOV":
			out = new Request(Type.MOVE,value.substring(3));
			break;
		case "REG":
			out = new Request(Type.REGISTER,value.substring(3));
			break;
		case "WAR":
			out = new Request(Type.WARNING,value.substring(3));
			break;
		default:
			out = new Request(Type.WARNING,"wrong format!");
			break;
		}
		return out;
	}

	public static String getMessage(Request request)
	{
		String out = null;
		
		switch(request.getType())
		{
		case ACCEPT:
			out = "ACC" + request.getValue();
			break;
		case DENY:
			out = "DNY" + request.getValue();
			break;
		case EXIT:
			out = "EXT" + request.getValue();
			break;
		case GAMEOVER:
			out = "GMO" + request.getValue();
			break;
		case LOGIN:
			out = "LOG" + request.getValue();
			break;
		case MESSAGE:
			out = "MSG" + request.getValue();
			break;
		case MOVE:
			out = "MOV" + request.getValue();
			break;
		case REGISTER:
			out = "REG" + request.getValue();
			break;
		case WARNING:
			out = "WAR" + request.getValue();
			break;
		default:
			break;
		}
		return out;
	}
	
	public static Account getAccount(String line)
	{
		int i;
		i=line.indexOf(':');
		String username = line.substring(0, i);
		line = line.substring(i+1);
		
		i=line.indexOf(':');
		String password = line.substring(0, i);
		line = line.substring(i+1);
		
		i=line.indexOf(':');
		String email = line.substring(0, i);
		line = line.substring(i+1);
		
		
		String nickname = line;
		

		
		Account out = new Account(username,password,nickname,email);
		System.out.println(out.toString());
		
		return out;
	}
	
}
