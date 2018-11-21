package db;

import java.util.List;

import exception.DBException;
import model.Product;

public interface ProductRepository {

	 Product get(String id) throws DBException;
	 List<Product> getAll() throws DBException;
	 void add(Product product) throws DBException;
	 void update(Product product) throws DBException;
	 void delete(String productId) throws DBException;

}