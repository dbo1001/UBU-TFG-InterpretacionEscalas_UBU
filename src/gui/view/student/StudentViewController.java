package gui.view.student;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import connection.ConnectionException;
import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

public class StudentViewController extends Controller {
	
	@FXML
	TextField name;
	@FXML
	TextField surname1;
	@FXML
	TextField surname2;
	@FXML
	TextField NIF;
	@FXML
	TextField direction;
	@FXML
	TextArea description;
	@FXML
	DatePicker date;
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
	private void acept() throws ConnectionException {
		Alumno stu = new Alumno();
		LocalDate localDate = this.date.getValue();
		Date date = null;
		if(localDate != null) {
				date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
		}
		stu.setNif(this.NIF.getText());
		stu.setNombre(this.name.getText());
		stu.setApellido1(this.surname1.getText());
		stu.setApellido2(this.surname2.getText());
		stu.setFechaNacimiento(date);
		stu.setDireccion(this.direction.getText());
		stu.setNotas(this.description.getText());
		Main.getStudentService().add(stu);
	}

}
