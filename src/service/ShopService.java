package service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import db.*;
import exception.DBException;
import exception.ModelException;
import model.Product;
import model.ProductOrder;
import model.User;

public class ShopService {
	private UserRepository userRepository;
	private ProductRepository productRepository;

	public ShopService(Properties properties) throws DBException{
		userRepository = new UserRepositoryInMemory();
		productRepository = new ProductRepositoryInMemory();
	}
	
	// PERSON
	
	public User getUser(String id) throws DBException, ModelException {
		return getUserRepository().get(id);
	}

	public List<User> getUsers() throws DBException, ModelException {
		return getUserRepository().getAll();
	}

	public void addUser(User user) throws DBException {
		getUserRepository().add(user);
	}

	public void updateUser(User user) throws DBException {
		getUserRepository().update(user);
	}

	public void deleteUser(String id) throws DBException {
		getUserRepository().delete(id);
	}

	private UserRepository getUserRepository() {
		return userRepository;
	}
	
	public boolean checkPassword(String password, String id) throws NoSuchAlgorithmException, UnsupportedEncodingException, DBException, ModelException {
		User u = getUser(id);
		return u.isCorrectPassword(password);
	}
	
	public User getUserIfAuthenticated(String id, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, ModelException {
		User u;
		try {
			u = getUser(id);
			if(!u.isCorrectPassword(password)) {
				u = null;
			}
		} catch (DBException e) {
			u = null;
		}
		
		return u;
	}
	
	// PRODUCT
	
	public Product getProduct(String productId) throws DBException {
		return getProductRepository().get(productId);
	}
	
	public List<Product> getProducts() throws DBException{
		return getProductRepository().getAll();
	}
	
	public void addProduct(Product p) throws DBException{
		getProductRepository().add(p);
	}
	
	public void updateProduct(Product p) throws DBException {
		getProductRepository().update(p);
	}
	
	public void deleteProduct(String id) throws DBException{
		getProductRepository().delete(id);
	}
	
	private ProductRepository getProductRepository() {
		return productRepository;
	}

}
