package classes;

public class User {
	
	public String fullName;
	public String firstName;
	public String lastName;
	public User(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.fullName = firstName+ ", "+ lastName;
	}
	
	public String getName(){
		return this.fullName;
	}
	
}
