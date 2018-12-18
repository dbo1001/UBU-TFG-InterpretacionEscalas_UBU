package gui.view.student;

import java.io.IOException;
import java.util.List;

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

public class StudentViewController extends Controller {
	
	@FXML
	private ChoiceBox<Aula> aulaCB;
	private List<Aula> listAllClassrooms;
	
	private void loadClassrooms() {
		ObservableList<Aula> obsList = FXCollections.observableArrayList();
		obsList.addAll(this.listAllClassrooms);
		
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
	
	public void setClassrooms(List<Aula> allClassrooms) {
		this.listAllClassrooms = allClassrooms;
		this.loadClassrooms();
	}
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar alumno.");
	}

}
