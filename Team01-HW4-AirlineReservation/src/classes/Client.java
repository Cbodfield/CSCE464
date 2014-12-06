package classes;

public class Client {
	public User user;
	public Organization organization;
	public Client(User user, Organization org){
		this.user = user;
		this.organization = org;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public Organization getOrganization(){
		return this.organization;
	}
	
}
