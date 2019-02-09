package gui;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller {
	protected final Pattern intPattern = Pattern.compile("[^0-9]");
	
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

}
