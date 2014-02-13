package concert;

import java.util.Date;

public class Client {

	private String firstName;
	private String lastName;
	private Date   birth;
	private String email;
	private String login;
	private String password;
	
	public Client(String firstName,	String lastName, Date birth, String email, String login, String password){
		this.firstName 	= firstName;
		this.lastName 	= lastName;
		this.birth 		= birth;
		this.email 		= email;
		this.login 		= login;
		this.password 	= password;
	}

	public String getFirstName(){return firstName;}
	public String getLastName(){return lastName;}
	public Date getBirth(){return birth;}
	public String getEmail(){return email;}
	public String getLogin(){return login;}
	public String getPassword(){return password;}
	
	@Override
	public String toString(){
		return "Client : " + firstName + " " + lastName;
	}
}
