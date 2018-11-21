package db;

import java.util.List;

import exception.DBException;
import exception.ModelException;
import model.User;

public interface UserRepository {

	 User get(String id) throws DBException, ModelException;
	 List<User> getAll() throws DBException, ModelException;
	 void add(User user) throws DBException;
	 void update(User user) throws DBException;
	 void delete(String id) throws DBException;

}