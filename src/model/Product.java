package model;

public class Product {

	private String id;
	private String description;
	private double price;
	
	public Product(String productID, String description, double price) {
		setId(productID);
		setDescription(description);
		setPrice(price);
	}
	
	public Product() {
		
	}
	
	//GETTERS

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}
	
	//SETTERS

	public void setId(String id) {
		if(id == null || id.isEmpty()) {
			throw new IllegalArgumentException("No product ID given.");
		}
		this.id = id;
	}

	public void setDescription(String description) {
		if(description == null || description.isEmpty()) {
			throw new IllegalArgumentException("No description given.");
		}

		this.description = description;
	}

	public void setPrice(double price) {
		if(price <= 0) {
			throw new IllegalArgumentException("Price not valid.");
		}
		
		this.price = price;
	}
	
}
