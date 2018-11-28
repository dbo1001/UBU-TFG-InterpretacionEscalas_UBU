package gui.view.classroom;

import java.io.IOException;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import model.Aula;
import service.ClassroomServiceImpl;
import service.Service;

public class ClassroomViewController extends Controller {
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar aula.");
	}

}
