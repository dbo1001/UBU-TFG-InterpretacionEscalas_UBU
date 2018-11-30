package gui.view.student;

import java.io.IOException;

import connection.service.ClassroomServiceImpl;
import connection.service.Service;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import model.Aula;

public class StudentViewController extends Controller {
	
	@FXML
	private ChoiceBox<Aula> aulaCB;
	private ClassroomServiceImpl classroomService = new ClassroomServiceImpl();
	
	@FXML
	private void initialize() {
		ObservableList<Aula> obsList = FXCollections.observableArrayList();
		obsList.addAll(classroomService.getAll());
		
		aulaCB.setItems(obsList);
		aulaCB.getSelectionModel().selectFirst();
		aulaCB.setConverter(new StringConverter<Aula>() {

			@Override
			public Aula fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Aula object) {
				return object.getNombre();
			}
			
		});
		
	}
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar alumno.");
	}

}
