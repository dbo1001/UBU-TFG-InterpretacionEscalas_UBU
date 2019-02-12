package gui.view.student;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import connection.ConnectionException;
import gui.Controller;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

/**
 * Controlador de la pantalla usada para editar alumnos
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class EditStudentViewController extends Controller {

	@FXML
	TextField name;
	@FXML
	TextField surname1;
	@FXML
	TextField surname2;
	@FXML
	TextField code;
	@FXML
	TextField direction;
	@FXML
	TextArea description;
	@FXML
	DatePicker date;
	@FXML
	private ChoiceBox<Aula> aulaCB;

	private List<Aula> listAllClassrooms;
	private Alumno stu;

	/**
	 * Inicializa el menú desplegable con las aulas
	 */
	@FXML
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

	/**
	 * Inicializa los datos del alumno
	 * @param stu alumno a editar
	 * @param allClassrooms lista con todas las aulas
	 */
	public void setStudentAndClassrooms(Alumno stu, List<Aula> allClassrooms) {
		this.stu = stu;
		this.listAllClassrooms = allClassrooms;
		this.fillFields();
	}

	/***
	 * Rellena los campos con los datos del alumno a editar
	 */
	@FXML
	private void fillFields() {
		this.name.setText(stu.getNombre());
		this.surname1.setText(stu.getApellido1());
		this.surname2.setText(stu.getApellido2());
		this.code.setText(stu.getCodigo());
		this.direction.setText(stu.getDireccion());
		this.description.setText(stu.getNotas());
		
		if(!Main.getCurrentTeacher().getPermisos()) {
			this.aulaCB.setDisable(true);
		}

		Date fecha = stu.getFechaNacimiento();
		if (fecha != null) {
			this.date.setValue(Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
		}

		this.loadClassrooms();
		Aula aula = stu.getAula();
		if (aula != null) {
			this.aulaCB.getSelectionModel().clearSelection();
			this.aulaCB.getSelectionModel().select(aula);
		}
	}

	/**
	 * Cancela la operación y vuelve atrás
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}
	}

	/**
	 * Comienza la transacción para editar los datos del alumno 
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void acept() throws IOException {
		LocalDate localDate = this.date.getValue();
		Date date = null;
		if (localDate != null) {
			date = Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
		}
		
		stu.setCodigo(this.code.getText());
		stu.setNombre(this.name.getText());
		stu.setApellido1(this.surname1.getText());
		stu.setApellido2(this.surname2.getText());
		stu.setFechaNacimiento(date);
		stu.setDireccion(this.direction.getText());
		stu.setNotas(this.description.getText());
		stu.setAula(this.aulaCB.getSelectionModel().getSelectedItem());
		
		try {
			if (Main.getStudentService().edit(stu)) {
				Alert alert = new Alert(AlertType.INFORMATION, "El alumno se ha modificado correctamente",
						ButtonType.OK);
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
				Main.setModifiedData(false);
				Main.showManageView();
			}
		} catch (ConnectionException cEx) {
			Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
			alert.initOwner(Main.getPrimaryStage());
			alert.showAndWait();
		}
	}

}
