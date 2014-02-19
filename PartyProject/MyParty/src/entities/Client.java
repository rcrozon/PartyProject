package entities;

import java.util.Date;

public class Client implements Entity {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String login;
	private String password;
	private int admin;
	private String dateCreated;
	
	public Client(int id, String firstName,	String lastName, String email, String login, String password,
			int admin, String dateCreated){
		this.id = id;
		this.firstName 	= firstName;
		this.lastName 	= lastName;
		this.email 		= email;
		this.login 		= login;
		this.password 	= password;
		this.admin = admin;
		this. dateCreated = dateCreated;
	}

	public int getId(){return id;}
	public String getFirstName(){return firstName;}
	public String getLastName(){return lastName;}
	public String getEmail(){return email;}
	public String getLogin(){return login;}
	public String getPassword(){return password;}
	public int getAdmin(){return admin;}
	public String getDateCreated(){return dateCreated;}
	
	@Override
	public String toString(){
		return "Client : " + firstName + " " + lastName;
	}
}
