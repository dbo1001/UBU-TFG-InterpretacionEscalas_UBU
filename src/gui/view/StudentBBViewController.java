package gui.view;

import gui.Main;
import javafx.fxml.FXML;

public class StudentBBViewController {

	@FXML
	private void cancel() {
		System.out.println("Cancelar");
	}
	
	@FXML
	private void acept(){
		System.out.println("Aceptar");
	}
	
	@FXML
	private void handCursor() {
		Main.handCursor();
	}
	
	@FXML
	private void defaultCursor() {
		Main.defaultCursor();
	}
}
