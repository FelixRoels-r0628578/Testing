package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import exception.ModelException;
import model.Product;

public class Cart {
	private Date creationDate;
	private List<ProductOrder> productsOrdered = new ArrayList<>();
	
	public List<ProductOrder> getProductsOrdered() {
		return productsOrdered;
	}

	public Cart() {
		this.creationDate = new Date();
	}
	
	public void addProduct(Product product, int quantity) throws ModelException {
		ProductOrder order = new ProductOrder(product, quantity);
		productsOrdered.add(order);
	}
	
	public ProductOrder getOrder(String id) {
		for(ProductOrder order : productsOrdered){
			if(order.getProductId().equals(id)){
				return order;
			}
		}
		return null;
	}

	public void replaceQuantityOrdered(String productId, int quantity) throws ModelException {
		if (quantity < 0) {
			throw new ModelException("Quantity cannot be lower than 0.");
		}
		if(quantity == 0) {
			deleteProduct(productId);
		} else {
			ProductOrder order = getOrder(productId);
			order.setQuantity(quantity);
		}
	}

	public void deleteProduct(String productId) {
		ProductOrder order = getOrder(productId);
		productsOrdered.remove(order);
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public double getTotalPrice() {
		double total = 0;
		Collection<ProductOrder> orders = productsOrdered;
		for(ProductOrder order : orders){
			total += order.getTotalPrice();
		}
		return total;
	}


	public int getNumberOfProductOrdersInCart() {
		return productsOrdered.size();
	}

}
