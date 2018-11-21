package model;

import exception.ModelException;
import model.Product;

public class ProductOrder {
	private Product product;
	private int quantity;
	
	public ProductOrder(Product product, int quantity) throws ModelException {
		setProduct(product);
		setQuantity(quantity);
	}

	public void setQuantity(int quantity) throws ModelException {
		if (quantity <= 0) {
			throw new ModelException("Quantity cannot be lower than or equal to 0.");
		}
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	private void setProduct(Product product) {
		this.product = product;
	}

	public double getTotalPrice() {
		return getProduct().getPrice() * getQuantity();
	}

	public String getProductDescription() {
		return getProduct().getDescription();
	}

	public double getProductPrice() {
		return getProduct().getPrice();
	}

	public String getProductId() {
		return getProduct().getId();
	}

	public void setPrice(double newPrice) {
		product.setPrice(newPrice);
	}

}
