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
import exception.ModelException;
import model.Role;
import model.User;

public class UserRepositorySQL implements UserRepository {
	
	private PreparedStatement statement;
	private Connection connection;
	private Properties properties;
	private String url;
	private String schema;

	public UserRepositorySQL(Properties properties) throws DBException {
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
	public User get(String id) throws DBException, ModelException {
		if(id == null || id.isEmpty()) {
			throw new DBException("No user ID given.");
		}
		
		openConnection();
		
		try {
			String sql = "SELECT * FROM " + schema + "person "
					+ "WHERE LOWER(userID) = LOWER(?)";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No user with given ID found.");
			}
			
			result.next();
			
			String email = result.getString("email");
			String password = result.getString("password");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String salt = result.getString("salt");
			Role role = Role.valueOf(result.getString("role"));
			
			User u = new User(id, password, salt, role, email, firstName, lastName);
		
			return u;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public List<User> getAll() throws DBException, ModelException {
		openConnection();
		
		try {
			String sql = "SELECT * FROM " + schema + "person ORDER BY userID ASC";
			statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No users found.");
			}
			
			List<User> users = new ArrayList<User>();
			
			while(result.next()) {
				String id = result.getString("userID");
				String email = result.getString("email");
				String password = result.getString("password");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String salt = result.getString("salt");
				Role role = Role.valueOf(result.getString("role"));

				User u = new User(id, password, salt, role, email, firstName, lastName);
				users.add(u);
			}
		
			return users;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void add(User user) throws DBException {
		if(user == null) {
			throw new DBException("No user to add.");
		}
		
		openConnection();
			
		try {			
			String sql = "SELECT * FROM " + schema + "person "
						+ "WHERE userID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			
			if(result.isBeforeFirst()) {
				throw new DBException("User already exists");
			}
		
			sql = "INSERT INTO " + schema + "person (userID, email, password, firstName, lastName, salt, role)"
						+ "VALUES(?,?,?,?,?,?,?)";

			statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getId());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getFirstName());
			statement.setString(5, user.getLastName());
			statement.setString(6, user.getSalt());
			statement.setString(7, user.getRole().toString());
			
			statement.executeUpdate();
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void update(User user) throws DBException {		
		if(user == null) {
			throw new DBException("No user to updat.e");
		}
		
		openConnection();

		try {
			
			String sql = "SELECT * FROM " + schema + "person "
						+ "WHERE userID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			
			if(!result.isBeforeFirst()) {
				throw new DBException("No user found with given ID.");
			}
			
			sql = "UPDATE " + schema + "person "
						+ "SET email = ?, password = ?, firstName = ?, lastName = ?, salt = ? , role = ? "
						+ "WHERE userID = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getSalt());
			statement.setString(6, user.getRole().toString());
			statement.setString(7, user.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void delete(String id) throws DBException {	
		if(id == null || id.isEmpty()) {
			throw new DBException("No user ID given.");
		}
		
		openConnection();
		
		try {
			String sql = "SELECT * FROM " + schema + "person "
						+ "WHERE userID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			
			ResultSet result = statement.executeQuery();
				
			if(!result.isBeforeFirst()) {
				throw new DBException("No user with given ID found.");
			}
			
			sql = "DELETE FROM " + schema + "person "
				+ "WHERE userID = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

}
