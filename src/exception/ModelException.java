package exception;

@SuppressWarnings("serial")
public class ModelException extends Exception {

	public ModelException(String message) {
		super(message);
	}
	
	public ModelException(String message, Exception e) {
		super(message, e);
	}
}
