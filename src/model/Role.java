package model;

public enum Role {
	
	ADMINISTRATOR("Administrator"),
	CUSTOMER("Customer");	
	
	private String name;
	
	Role(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
