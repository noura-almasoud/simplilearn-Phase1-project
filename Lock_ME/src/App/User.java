package App;

public class User {
	
	private String UserName;
	private String Password;
	
	
	public User() {
	}

	public User(String userName, String password) {
		this.UserName = userName;
		this.Password = password;
	}

	
	public String getUserName() {
		return UserName;
	}
	
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	
	public String getPassword() {
		return Password;
	}
	
	public void setPassword(String password) {
		Password = password;
	}
	
	

}
