package exception;

@SuppressWarnings("serial")
public class DBException extends Exception {

	public DBException(String message) {
		super(message);
	}
	
	public DBException(String message, Exception e) {
		super(message, e);
	}
}
