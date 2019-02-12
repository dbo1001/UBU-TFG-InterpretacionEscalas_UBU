package gui.view.classroom;

import java.io.IOException;
import java.util.ArrayList;
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
 * Controlador de la pantalla para editar aulas
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class EditClassroomViewController extends SelectorController<Profesor> {

	@FXML
	private TextField name;
	@FXML
	private TextField capacity;
	@FXML
	private TextArea description;
	
	private List<Profesor> listAllTeachers = new ArrayList<Profesor>();
	private Aula cla;
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
	 * Rellena los campos con los datos del aula a editar
	 * @param cla aula
	 * @param listAllTeachers lista de todos los profesores, para poder asignarlos al aula
	 */
	public void setClassroom(Aula cla, List<Profesor> listAllTeachers) {
		this.cla = cla;
		this.listAllTeachers = listAllTeachers;
		this.fillFields();
	}

	/**
	 * Rellena los campos
	 */
	@FXML
	private void fillFields() {
		this.name.setText(cla.getNombre());
		this.capacity.setText("" + cla.getCapacidad());
		this.description.setText(cla.getNotas());
		
		super.initialize(callback, this.listAllTeachers, new SortTeacher());
		super.getSelectedObjects().addAll(cla.getProfesors());
		super.getDisplayedObjects().removeAll(new ArrayList<Profesor>(cla.getProfesors()));
		super.sortObjects();
	}

	/**
	 * Cancela la edición del aula
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
	 * Edita el aula
	 * @throws IOException archino no encontrado
	 */
	@FXML
	private void acept() throws IOException {
		if (this.capacity.getText().length() > 0 && !super.intPattern.matcher(this.capacity.getText()).find()) {
			cla.setCapacidad(Integer.parseInt(this.capacity.getText()));
			cla.setNombre(this.name.getText());
			cla.setNotas(this.description.getText());
			cla.setProfesors(new HashSet<Profesor>(super.getSelectedObjects()));

			try {
				if (Main.getClassroomService().edit(cla)) {
					Alert alert = new Alert(AlertType.INFORMATION, "El aula se ha editado correctamente",
							ButtonType.OK);
					alert.showAndWait();
					Main.setModifiedData(false);
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
				alert.showAndWait();
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR, ConnectionError.WRONG_CAPACITY.getText(), ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	/**
	 * Sublase para ordenar los profesores
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
