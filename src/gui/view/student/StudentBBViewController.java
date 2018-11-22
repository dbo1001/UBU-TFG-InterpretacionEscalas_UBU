package gui.view.student;

import java.io.IOException;

import gui.Main;
import gui.view.Controller;
import javafx.fxml.FXML;

public class StudentBBViewController extends Controller {

	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.showManageView();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar alumno.");
	}

}
