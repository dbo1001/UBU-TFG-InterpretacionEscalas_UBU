package connection;

public enum ConnectionError {
	WRONG_NAME("ERROR: El nombre solo debe contener letras."),
	WRONG_CLASSROOM_NAME("ERROR: El nombre no puede contener carácteres especiales."),
	WRONG_SURNAME("ERROR: Los apellidos deben contener solo letras."),
	WRONG_DATE("ERROR: La fecha no puede ser posterior a la fecha actual."),
	WRONG_NIF("ERROR: El NIF debe tener un formato de 8 dígitos seguidos por una letra mayúscula."),
	WRONG_CAPACITY("ERROR: La capacidad debe ser un numero y no puede quedar vacía."),
	NAME_TOO_LONG("ERROR: La longitud del nombre debe ser inferior a 50 carácteres."),
	SURNAME_TOO_LONG("ERROR: La longitud de los apellidos debe ser inferior a 75 carácteres."),
	DIRECTION_TOO_LONG("ERROR: La longitud de la dirección no puede ser superior a 150 carácteres."),
	DESCRIPTION_TOO_LONG("ERROR: La longitud de la descripción no puede ser superior a 1000 carácteres."),
	CLASSROOM_IS_FULL("ERROR: La clase a la que estas intentando añadir al alumno esta llena."),
	FIELD_IS_EMPTY("ERROR: Los campos marcados con * son obligatorios y no pueden quedar en blanco.");
	
	
	private String text;
	
	private ConnectionError(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
