package gui.view.classroom;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import connection.ConnectionError;
import connection.ConnectionException;
import gui.Main;
import gui.SelectorController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import model.Aula;
import model.Profesor;

/**
 * Controlador de la pantalla de creación de aulas
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class ClassroomViewController extends SelectorController<Profesor> {

	@FXML
	TextField name;
	@FXML
	TextField capacity;
	@FXML
	TextArea description;
	private final Callback<ListView<Profesor>, ListCell<Profesor>> callback = new Callback<ListView<Profesor>, ListCell<Profesor>>() {

		@Override
		public ListCell<Profesor> call(ListView<Profesor> teachers) {
			ListCell<Profesor> cellsList = new ListCell<Profesor>() {
				@Override
				protected void updateItem(Profesor tea, boolean empty) {
					super.updateItem(tea, empty);
					if (tea != null) {
						setText(tea.getApellido1() + " " + tea.getApellido2() + ", " + tea.getNombre());
					} else {
						setText("");
					}
				}
			};
			return cellsList;
		}
	};

	/**
	 * Cancela la creación del aula
	 * 
	 * @throws IOException archino no encontrado
	 */
	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}

	}

	/**
	 * Crea el aula
	 * @throws IOException archino no encontrado
	 */
	@FXML
	private void acept() throws IOException {
		Aula cla = new Aula();
		if (this.capacity.getText().length() > 0 && !super.intPattern.matcher(this.capacity.getText()).find()) {
			cla.setCapacidad(Integer.parseInt(this.capacity.getText()));
			cla.setNombre(this.name.getText());
			cla.setNotas(this.description.getText());
			cla.setProfesors(new HashSet<Profesor>(super.getSelectedObjects()));

			try {
				if (Main.getClassroomService().add(cla)) {
					Alert alert = new Alert(AlertType.INFORMATION, "El nuevo aula se ha creado correctamente",
							ButtonType.OK);
					alert.showAndWait();
					Main.setModifiedData(false);
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, ConnectionError.WRONG_CAPACITY.getText(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	/**
	 * Inicializa los elementos de selección de profesores
	 * @param allTeachers todos los profesores de la base de datos
	 */
	public void setTeachers(List<Profesor> allTeachers) {
		super.initialize(callback, allTeachers, new SortTeacher());
	}

	/**
	 * Subclase para ordenar los profesores
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	private class SortTeacher implements Comparator<Profesor> {
		@Override
		public int compare(Profesor p1, Profesor p2) {
			String p1Display = "" + p1.getApellido1() + " " + p1.getApellido2() + ", " + p1.getNombre();
			String p2Display = "" + p2.getApellido1() + " " + p2.getApellido2() + ", " + p2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(p1Display, p2Display);
		}

	}

}
