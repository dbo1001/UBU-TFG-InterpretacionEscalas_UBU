package gui;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller {
	protected final Pattern intPattern = Pattern.compile("[^0-9]");
	protected final Pattern passPattern = Pattern.compile("[,;.:\"\' ]");
	
	@FXML
	protected void handCursor() {
		Main.handCursor();
	}
	
	@FXML
	protected void defaultCursor() {
		Main.defaultCursor();
	}
	
	@FXML
	protected void loadCursor() {
		Main.loadCursor();
	}
	
	@FXML
	protected boolean cancelAlert() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que quieres descartar los cambios y volver atrás?",
				ButtonType.NO, ButtonType.YES);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES) {
			return true;
		}else {
			return false;
		}
		
	}
	
	@FXML
	protected void goBack() {
		Main.goBack();
	}
	
	protected static String encryptPassword(String pass) {
		String securePass = "";
		
		try {
			byte[] salt = getSalt();
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
	
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

}
