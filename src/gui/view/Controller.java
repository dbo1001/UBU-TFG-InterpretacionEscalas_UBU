package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller {
	
	@FXML
	private void handCursor() {
		Main.handCursor();
	}
	
	@FXML
	private void defaultCursor() {
		Main.defaultCursor();
	}
	
	@FXML
	protected boolean cancelAlert() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que quieres descartar los cambios y volver atrás?",
				ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES) {
			return true;
		}else {
			return false;
		}
		
	}

}
