package connection;

public enum ConnectionError {
	WRONG_NAME("ERROR: El nombre solo debe contener letras."),
	WRONG_CLASSROOM_NAME("ERROR: El nombre no puede contener car�cteres especiales."),
	WRONG_SURNAME("ERROR: Los apellidos deben contener solo letras."),
	WRONG_DATE("ERROR: La fecha no puede ser posterior a la fecha actual."),
	WRONG_NIF("ERROR: El NIF debe tener un formato de 8 d�gitos seguidos por una letra may�scula."),
	WRONG_CAPACITY("ERROR: La capacidad debe ser un numero y no puede quedar vac�a."),
	NAME_TOO_LONG("ERROR: La longitud del nombre debe ser inferior a 50 car�cteres."),
	SURNAME_TOO_LONG("ERROR: La longitud de los apellidos debe ser inferior a 75 car�cteres."),
	DIRECTION_TOO_LONG("ERROR: La longitud de la direcci�n no puede ser superior a 150 car�cteres."),
	DESCRIPTION_TOO_LONG("ERROR: La longitud de la descripci�n no puede ser superior a 1000 car�cteres."),
	CLASSROOM_IS_FULL("ERROR: La clase a la que estas intentando a�adir al alumno esta llena."),
	FIELD_IS_EMPTY("ERROR: Los campos marcados con * son obligatorios y no pueden quedar en blanco.");
	
	
	private String text;
	
	private ConnectionError(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
