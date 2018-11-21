package model;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import exception.ModelException;

public class User {

	private String id;
	private Person person;
	private String password;
	private String salt = null;
	private Role role = Role.CUSTOMER;

	public User(String id, String password,  String salt, Role role, 
			String email, String firstName, String lastName) throws ModelException {
		this(id, password, salt, role, new Person(email, firstName, lastName));
	}
	
	public User(String id, String password, String salt, Role role, Person person) throws ModelException {
		setId(id);
		setPassword(password);
		setSalt(salt);
		setRole(role);
		setPerson(person);
	}

	public User() {
		this.person = new Person();
	}

	// PASSWORD
	
	

	/**
	 * Used by database classes to set password field
	 * 
	 * @param password
	 *            The already hashed password
	 * @throws ModelException
	 */
	public void setPassword(String password) throws ModelException {
		if (password == null || password.isEmpty()) {
			throw new ModelException("No password given.");
		}
		this.password = password.trim();
	}

	/**
	 * When a new user registers, this method is used to set the password
	 * 
	 * @param password
	 *            The password in plain text, not hashed
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws ModelException
	 */
	public void setAndHashPassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, ModelException {
		if (password == null || password.isEmpty()) {
			throw new ModelException("No password given.");
		}

		String hashedPassword = hashPassword(password);
		setPassword(hashedPassword);
	}

	/**
	 * This method hashes a password
	 * 
	 * @param password
	 *            The password in plain text, not hashed
	 * @return The hashed password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();

		crypt.update(getSalt().getBytes("UTF-8"));
		crypt.update(password.getBytes("UTF-8"));

		byte[] digest = crypt.digest();
		String hashedPassword = new BigInteger(1, digest).toString(16);

		return hashedPassword;
	}

	/**
	 * This method checks if the given parameter's hash equals the Person's
	 * password
	 * 
	 * @param password
	 *            The password the user has entered to check
	 * @return True if the given String's hash equals the Person's password,
	 *         false otherwise
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws ModelException
	 */
	public boolean isCorrectPassword(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, ModelException {
		if (password == null || password.isEmpty()) {
			throw new ModelException("No password given.");
		}

		String hashedPassword = hashPassword(password);
		return getPassword().equals(hashedPassword);
	}

	public String getPassword() {
		return password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSalt() {
		if (this.salt == null) {
			setSalt(generateSalt());
		}
		return this.salt;
	}

	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte seed[] = random.generateSeed(20);
		return new BigInteger(1, seed).toString(16);
	}
	
	//PERSON-SPECIFIC GETTERS & SETTERS
	public String getEmail() {
		return this.person.getEmail();
	}

	public String getFirstName() {
		return this.person.getFirstName();
	}
	
	public String getLastName() {
		return this.person.getLastName();
	}
	
	public void setEmail(String email) throws ModelException {
		this.person.setEmail(email);
	}

	public void setFirstName(String firstName) throws ModelException {
		this.person.setFirstName(firstName);
	}

	public void setLastName(String lastName) throws ModelException {
		this.person.setLastName(lastName);
	}

	//OTHER GETTERS  & SETTERS
	
	public String getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setId(String id) throws ModelException {
		if(id == null || id.isEmpty()){
			throw new ModelException("No user ID given.");
		}
		this.id = id;
	}

	public void setPerson(Person person) throws ModelException {
		if(person == null) {
			throw new ModelException("No person given.");
		}
		this.person = person;
	}

	public void setRole(Role role) throws ModelException {
		if(role == null) {
			throw new ModelException("No role given.");
		}
		this.role = role;
	}

}
