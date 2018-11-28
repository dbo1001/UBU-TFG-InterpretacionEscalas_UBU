package gui.view.classroom;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;
import model.Profesor;
import service.ClassroomServiceImpl;
import service.Service;

public class EditClassroomViewController extends Controller {

	@FXML
	private TextField nombre;
	@FXML
	private TextField capacidad;
	
	private Service<Aula> classroomService = new ClassroomServiceImpl();
	private Aula cla;

	public void setClassroom(Aula cla) {
		this.cla = cla;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
		this.nombre.setText(cla.getNombre());
		this.capacidad.setText("" + cla.getCapacidad());
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y sobreescibir los cambios del aula.");
	}

}
