package classes;

public class Organization {
	public String address="";
	public String name="";
	public Organization(String name, String address){
		this.address = address;
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
