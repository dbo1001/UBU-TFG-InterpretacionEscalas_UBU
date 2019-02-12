package gui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * Superclase para los controladores de los archivos .fxml
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public abstract class Controller {
	protected final Pattern intPattern = Pattern.compile("[^0-9]");
	protected final Pattern passPattern = Pattern.compile("[,;.:\"\' ]");
	
	/**
	 * Cambia el cursor a una mano
	 */
	@FXML
	protected void handCursor() {
		Main.handCursor();
	}
	
	/**
	 * Cambia el cursor al cursor por defecto, la flecha
	 */
	@FXML
	protected void defaultCursor() {
		Main.defaultCursor();
	}
	
	/**
	 * Cambia el cursor al cursor que indica que se estan cargando datos
	 */
	@FXML
	protected void loadCursor() {
		Main.loadCursor();
	}
	
	/**
	 * Pregunta al usuario si quiere descartar los cambios actuales
	 * @return respuesta del usuario
	 */
	@FXML
	protected boolean cancelAlert() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que quieres descartar los cambios y volver atrás?",
				ButtonType.NO, ButtonType.YES);
		
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();
		
		if(alert.getResult() == ButtonType.YES) {
			return true;
		}else {
			return false;
		}
		
	}
	
	/**
	 * Carga la pantalla anterior
	 */
	@FXML
	protected void goBack() {
		Main.goBack();
	}
	
	/**
	 * Encrypta la contraseña mediante el algoritmo de hashing SHA256
	 * @param pass contraseña a encryptar
	 * @return contraseña segura
	 */
	protected static String encryptPassword(String pass) {
		String securePass = "";
		
		try {
			//Si en algún momento se quiere usar sal, descomentar este par de lineas
			//byte[] salt = getSalt();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//md.update(salt);
			byte[] bytes = md.digest(pass.getBytes());
			StringBuilder sb = new StringBuilder();
			for(byte by: bytes) {
				sb.append(Integer.toString((by & 0xff) + 0x100, 16).substring(1));
			}
			
			securePass = sb.toString();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return securePass;
	}
	
	/**
	 * Genera sal aleatoria para la contraseña.
	 * No se usa porque no tenemos una columna en la tabla de profesores para guardar la sal.
	 * @return sal
	 * @throws NoSuchAlgorithmException algoritmo no encontrado
	 */
	@SuppressWarnings("unused")
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

}
