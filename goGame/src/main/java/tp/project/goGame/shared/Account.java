package tp.project.goGame.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "gamesWon")
	private int gamesWon;
	
	@Column(name = "gamesLost")
	private int gamesLost;
	
	public Account(){}
	
	public Account(String username,String password, String nickname, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.gamesWon = 0;
		this.gamesLost = 0;
	}
	
	public Account(String username,String password, String nickname, String email, int gamesWon,int gamesLost) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public int getGamesWon() {
		return this.gamesWon;
	}
	
	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}
	
	public int getGamesLost() {
		return this.gamesLost;
	}
	
	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}
	
	@Override
	public String toString(){
		return "Account: " + this.username + ", " + this.password + ", " + this.nickname + ", " + this.email + ", " + this.gamesWon + ", " + this.gamesLost; 
	}
	
	
}














