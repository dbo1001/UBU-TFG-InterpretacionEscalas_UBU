package gui.view.student;

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
import service.ClassroomServiceImpl;
import service.Service;

public class EditStudentViewController extends Controller {

	@FXML
	private TextField nombre;
	@FXML
	private TextField primerApellido;
	@FXML
	private TextField segundoApellido;
	@FXML
	private TextField NIF;
	@FXML
	private TextField direccion;
	@FXML
	private DatePicker fechaNacimiento;
	@FXML
	private ChoiceBox<Aula> aulaCB;
	
	private Service<Aula> classroomService = new ClassroomServiceImpl();
	private Alumno stu;
	
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

	public void setStudent(Alumno stu) {
		this.stu = stu;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
		this.nombre.setText(stu.getNombre());
		this.primerApellido.setText(stu.getApellido1());
		this.segundoApellido.setText(stu.getApellido2());
		this.NIF.setText(stu.getNif());
		this.direccion.setText(stu.getDireccion());

		Date fecha = stu.getFechaNacimiento();
		if (fecha != null) {
			this.fechaNacimiento
					.setValue(Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
		}
		
		Aula aula = stu.getAula();
		if(aula != null) {
			this.aulaCB.getSelectionModel().clearSelection();
			this.aulaCB.getSelectionModel().select(aula);
		}
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.showManageView();
		}
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y sobreescibir los cambios del alumno.");
	}

}
