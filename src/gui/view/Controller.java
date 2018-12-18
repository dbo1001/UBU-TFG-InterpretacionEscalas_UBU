package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller {
	
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
		Alert alert = new Alert(AlertType.CONFIRMATION, "�Est�s seguro de que quieres descartar los cambios y volver atr�s?",
				ButtonType.YES, ButtonType.NO);
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
