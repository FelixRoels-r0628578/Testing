package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.User;

public class UserRepositoryInMemory implements UserRepository {
	private Map<String, User> users;
	
	public UserRepositoryInMemory () {
		users = new TreeMap<String, User>(String.CASE_INSENSITIVE_ORDER);
	}
	
	@Override
	public User get(String id){
		if(id == null || id.isEmpty()){
			throw new IllegalArgumentException("No ID given.");
		}
		return users.get(id);
	}
	
	@Override
	public List<User> getAll(){
		return new ArrayList<User>(users.values());	
	}

	@Override
	public void add(User user){
		if(user == null){
			throw new IllegalArgumentException("No user given.");
		}
		if (users.containsKey(user.getId())) {
			throw new IllegalArgumentException("User already exists.");
		}
		users.put(user.getId(), user);
	}
	
	@Override
	public void update(User user){
		if(user == null){
			throw new IllegalArgumentException("No user given.");
		}
		if(!users.containsKey(user.getId())){
			throw new IllegalArgumentException("User not found.");
		}
		users.put(user.getId(), user);
	}
	
	@Override
	public void delete(String id){
		if(id == null || id.isEmpty()){
			throw new IllegalArgumentException("No id given.");
		}
		users.remove(id);
	}
}
