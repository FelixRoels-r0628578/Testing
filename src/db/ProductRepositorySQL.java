package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import exception.DBException;
import model.Product;

public class ProductRepositorySQL implements ProductRepository {

	private PreparedStatement statement;
	private Connection connection;
	private Properties properties;
	private String url;
	private String schema;
	
	public ProductRepositorySQL(Properties properties) throws DBException {
		this.properties = properties;
		this.url = properties.getProperty("url");
		this.schema = properties.getProperty("schema");
		this.schema += ".";
	}
	
	private void openConnection() throws DBException {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, properties);
		} catch(SQLException e) {
			throw new DBException(e.getMessage(), e);
		} catch(ClassNotFoundException e) {
			throw new DBException(e.getMessage(), e);
		}
	}
	
	private void closeConnection() throws DBException {
		try {
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	@Override
	public Product get(String id) throws DBException {
		openConnection();
				
		try {
			String sql = "SELECT * FROM " + schema + "product "
					+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No product with given ID found.");
			}
			
			result.next();
			
			String description = result.getString("description");
			Double price = result.getDouble("price");
			
			Product p = new Product(id, description, price);
		
			return p;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<Product> getAll() throws DBException {
		openConnection();
		
		try {
			String sql = "SELECT * FROM " + schema + "product ORDER BY productID ASC";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No products found.");
			}
			
			List<Product> products = new ArrayList<Product>();
			
			while(result.next()){
				String productID = result.getString("productID");
				String description = result.getString("description");
				Double price = result.getDouble("price");
				
				Product p = new Product(productID, description, price);
			
				products.add(p);
			}
			
			return products;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void add(Product product) throws DBException {
		if(product == null) {
			throw new DBException("No product to add");
		}
		
		openConnection();
			
		try {
			String sql = "SELECT * FROM " + schema + "product "
						+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, product.getId());
			
			ResultSet result = statement.executeQuery();
			
			if(result.isBeforeFirst()) {
				throw new DBException("Product already exists");
			}
		
			sql = "INSERT INTO " + schema + "product (productID, description, price)"
					+ "VALUES(?,?,?)";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, product.getId());
			statement.setString(2, product.getDescription());
			statement.setDouble(3, product.getPrice());

			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void update(Product product) throws DBException {
		if(product == null) {
			throw new DBException("No product to update");
		}
		
		openConnection();
			
		try {
			String sql = "SELECT * FROM " + schema + "product "
					+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, product.getId());
			
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No product with given ID found.");
			}
			
			sql = "UPDATE " + schema + "product "
					+ "SET description = ?, price = ? "
					+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, product.getDescription());
			statement.setDouble(2, product.getPrice());
			statement.setString(3, product.getId());
						
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void delete(String productId) throws DBException {
		openConnection();
		
		try {
			String sql = "SELECT * FROM " + schema + "product "
					+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, productId);
			
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No product with given ID found.");
			}
			
			sql = "DELETE FROM " + schema + "product "
				+ "WHERE productID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, productId);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

}
