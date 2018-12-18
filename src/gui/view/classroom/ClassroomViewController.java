package gui.view.classroom;

import java.io.IOException;

import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import model.Aula;

public class ClassroomViewController extends Controller {
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar aula.");
	}

}
