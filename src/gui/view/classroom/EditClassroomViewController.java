package gui.view.classroom;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import gui.view.SelectorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;
import model.Profesor;

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

	public void setClassroom(Aula cla, List<Profesor> listAllTeachers) {
		this.cla = cla;
		this.listAllTeachers = listAllTeachers;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
		this.name.setText(cla.getNombre());
		this.capacity.setText("" + cla.getCapacidad());
		this.description.setText(cla.getNotas());
		
		super.initialize(callback, this.listAllTeachers, new SortTeacher());
		super.getSelectedObjects().addAll(cla.getProfesors());
		super.getDisplayedObjects().removeAll(cla.getProfesors());
		super.sortObjects();
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}
	}

	@FXML
	private void acept() throws IOException {
		if (this.capacity.getText().length() > 0 && !super.intPattern.matcher(this.capacity.getText()).find()) {
			cla.setCapacidad(Integer.parseInt(this.capacity.getText()));
			cla.setNombre(this.name.getText());
			cla.setNotas(this.description.getText());
			cla.setProfesors(super.getSelectedObjects());

			try {
				if (Main.getClassroomService().edit(cla)) {
					Alert alert = new Alert(AlertType.INFORMATION, "La nueva aula se ha creado correctamente",
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
	
	private class SortTeacher implements Comparator<Profesor> {
		@Override
		public int compare(Profesor p1, Profesor p2) {
			String p1Display = "" + p1.getApellido1() + " " + p1.getApellido2() + ", " + p1.getNombre();
			String p2Display = "" + p2.getApellido1() + " " + p2.getApellido2() + ", " + p2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(p1Display, p2Display);	
		}
	}

}
