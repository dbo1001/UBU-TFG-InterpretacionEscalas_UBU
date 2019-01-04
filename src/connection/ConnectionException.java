package connection;

public class ConnectionException extends Exception {

	private ConnectionError error;
	private static final long serialVersionUID = 1L;
	
	public ConnectionException (String message) {
		super(message);
	}
	
	public ConnectionException (ConnectionError error) {
		super("");
		this.error = error;
	}
	
	public ConnectionException (ConnectionError error, String message) {
		super(message);
		this.error = error;
	}
	
	public ConnectionException (ConnectionError error, Exception ex) {
		super(ex);
		this.error = error;
	}
	
	public ConnectionException (ConnectionError error, String message, Exception ex) {
		super(message, ex);
		this.error = error;
	}
	
	public ConnectionError getError() {
		return this.error;
	}
	
	@Override
	public String getMessage() {
		if(error != null) {
			return error.getText() + super.getMessage();
		}else {
			return super.getMessage();
		}
	}

}
