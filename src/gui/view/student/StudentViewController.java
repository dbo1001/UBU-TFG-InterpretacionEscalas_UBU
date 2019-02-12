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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

/**
 * Controlador de la pantalla de creación de alumnos
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class StudentViewController extends Controller {

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

	/**
	 * Inicializa el menú desplegable con las aulas existentes
	 */
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
	 * Carga los datos de las aulas
	 * @param allClassrooms todas las aulas existentes
	 */
	public void setClassrooms(List<Aula> allClassrooms) {
		this.listAllClassrooms = allClassrooms;
		this.loadClassrooms();
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
	 * Comienza la transacción para insertar el nuevo alumno en la base de datos
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void acept() throws IOException {
		Alumno stu = new Alumno();
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
			if (Main.getStudentService().add(stu)) {
				Alert alert = new Alert(AlertType.INFORMATION, "El nuevo alumno se ha creado correctamente",
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
