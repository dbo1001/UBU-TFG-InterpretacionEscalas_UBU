package connection;

public enum ConnectionError {
	WRONG_NAME("ERROR: El nombre solo debe contener letras."),
	WRONG_CLASSROOM_NAME("ERROR: El nombre no puede contener car�cteres especiales."),
	WRONG_SURNAME("ERROR: Los apellidos deben contener solo letras."),
	WRONG_DATE("ERROR: La fecha no puede ser posterior a la fecha actual."),
	WRONG_NIF("ERROR: El NIF debe tener un formato de 8 d�gitos seguidos por una letra may�scula."),
	WRONG_CAPACITY("ERROR: La capacidad debe ser un numero y no puede quedar vac�a."),
	WRONG_PASSWORD_LENGTH("ERROR: La contrase�a debe tener como m�nimo 8 car�cteres y hasta un m�ximo de 64."),
	WRONG_ADMIN_RIGHTS("ERROR: No puedes quitarle los permisos de edici�n a la cuenta de administrador"),
	WRONG_CODE("ERROR: La longitud del c�digo debe ser inferior a 30 car�cteres."),
	CANT_DELETE_ADMIN("ERROR: No puedes eliminar la cuenta de administrador (ADMIN)."),
	CANT_DELETE_CLASSROOM("ERROR: No puedes borrar este aula porque a�n tiene alumnos asignados."),
	NAME_TOO_LONG("ERROR: La longitud del nombre debe ser inferior a 50 car�cteres."),
	SURNAME_TOO_LONG("ERROR: La longitud de los apellidos debe ser inferior a 75 car�cteres."),
	DIRECTION_TOO_LONG("ERROR: La longitud de la direcci�n no puede ser superior a 150 car�cteres."),
	DESCRIPTION_TOO_LONG("ERROR: La longitud de la descripci�n no puede ser superior a 1000 car�cteres."),
	CLASSROOM_IS_FULL("ERROR: La clase a la que estas intentando a�adir el alumno esta llena."),
	FIELD_IS_EMPTY("ERROR: Los campos marcados con * son obligatorios y no pueden quedar en blanco."),
	CLASSROOM_ALREADY_EXISTS("ERROR: Ya existe un aula con el mismo nombre."),
	TEACHER_ALREADY_EXISTS("ERROR: Ya existe un profesor con el mismo NIF.");
	
	
	private String text;
	
	private ConnectionError(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}
