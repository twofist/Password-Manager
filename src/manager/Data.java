package manager;
import java.io.Serializable;

public class Data implements Serializable{
	
	private String username, email, password;
	
	public Data(String password) {
		super();
		this.password = password;
	}
	
	void setUsername(String username) {
		this.username = username;
	}
	
	void setEmail(String email) {
		this.email = email;
	}
	
	void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String toString() {
		String str = username + " " + email + " " + password;
		return str;
	}
}
