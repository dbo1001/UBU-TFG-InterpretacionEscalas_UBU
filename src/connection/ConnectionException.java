package connection;
/**
 * Clase que lanza las excepciones cuando hay problemas en las transacciones
 * 
 * @author Mario Núñez Izquierdo
 *
 */
public class ConnectionException extends Exception {

	private ConnectionError error;
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param message Mensaje de la excepcion
	 */
	public ConnectionException (String message) {
		super(message);
	}
	
	/**
	 * Contructor
	 * @param error ConnectionError que contiene la el mensaje para el usuario
	 */
	public ConnectionException (ConnectionError error) {
		super("");
		this.error = error;
	}
	
	/**
	 * Constructor
	 * @param error ConnectionError que contiene la el mensaje para el usuario
	 * @param message mensaje de error
	 */
	public ConnectionException (ConnectionError error, String message) {
		super(message);
		this.error = error;
	}
	
	/**
	 * Constructor
	 * @param error ConnectionError que contiene la el mensaje para el usuario
	 * @param ex excepción anterior
	 */
	public ConnectionException (ConnectionError error, Exception ex) {
		super(ex);
		this.error = error;
	}
	
	/**
	 * Constructor
	 * @param error ConnectionError que contiene la el mensaje para el usuario
	 * @param message mensage del error
	 * @param ex excepción anterior
	 */
	public ConnectionException (ConnectionError error, String message, Exception ex) {
		super(message, ex);
		this.error = error;
	}
	
	/**
	 * Método getter
	 * @return error contenido
	 */
	public ConnectionError getError() {
		return this.error;
	}
	
	/**
	 * Devuelve el mensage del error contenido en esta clase
	 * @return mensage del error
	 */
	@Override
	public String getMessage() {
		if(error != null) {
			return error.getText() + super.getMessage();
		}else {
			return super.getMessage();
		}
	}

}
