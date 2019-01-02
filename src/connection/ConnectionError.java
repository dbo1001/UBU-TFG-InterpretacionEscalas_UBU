package connection;

public enum ConnectionError {
	WRONG_NAME(""),
	WRONG_SURNAME(""),
	WRONG_DATE(""),
	WRONG_NIF(""),
	NAME_TOO_LONG(""),
	SURNAME_TOO_LONG(""),
	DIRECTION_TOO_LONG(""),
	DESCRIPTION_TOO_LONG(""),
	CLASSROOM_IS_FULL("");
	
	
	private String text;
	
	private ConnectionError(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
