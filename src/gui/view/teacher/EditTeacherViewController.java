package gui.view.teacher;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

public class EditTeacherViewController extends SelectorController<Aula> {

	@FXML
	TextField name;
	@FXML
	TextField surname1;
	@FXML
	TextField surname2;
	@FXML
	TextField NIF;
	@FXML
	TextArea description;
	@FXML
	TextField password;
	@FXML
	ChoiceBox<String> rights;
	
	private List<Aula> listAllClassrooms;
	private Profesor tea;
	private final Callback<ListView<Aula>, ListCell<Aula>> callback = new Callback<ListView<Aula>, ListCell<Aula>>() {

		@Override
		public ListCell<Aula> call(ListView<Aula> classrooms) {
			ListCell<Aula> cellsList = new ListCell<Aula>() {
				@Override
				protected void updateItem(Aula cla, boolean empty) {
					super.updateItem(cla, empty);
					if (cla != null) {
						setText(cla.getNombre());
					} else {
						setText("");
					}
				}
			};
			return cellsList;
		}
	};
	
	@FXML
	private void initialize() {
		List<String> choices = new ArrayList<String>();
		choices.add("Sí");
		choices.add("No");
		this.rights.getItems().addAll(choices);
		this.rights.getSelectionModel().select("No");
	}
	

	public void setTeacherAndClassrooms(Profesor tea, List<Aula> allClassrooms) {
		this.tea = tea;
		this.listAllClassrooms = allClassrooms;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
	
		this.name.setText(tea.getNombre());
		this.surname1.setText(tea.getApellido1());
		this.surname2.setText(tea.getApellido2());
		this.NIF.setText(tea.getNif());
		this.description.setText(this.tea.getNotas());
		this.rights.getSelectionModel().select(this.tea.getPermisos() == true ? "Sí" : "No");
		this.password.setText(tea.getContrasena());
		
		
		super.initialize(callback, this.listAllClassrooms, new SortClassroom());
		super.getSelectedObjects().addAll(this.tea.getAulas());
		super.getDisplayedObjects().removeAll(this.tea.getAulas());
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
		tea.setNombre(this.name.getText());
		tea.setApellido1(this.surname1.getText());
		tea.setApellido2(this.surname2.getText());
		tea.setNif(this.NIF.getText());
		tea.setNotas(this.description.getText());
		tea.setContrasena(this.password.getText());
		tea.setPermisos(this.rights.getSelectionModel().getSelectedItem().equals("No") ? false : true);
		tea.setAulas(super.getSelectedObjects());

		try {
			if (Main.getTeacherService().edit(tea)) {
				Alert alert = new Alert(AlertType.INFORMATION, "El profesor se ha modificado correctamente",
						ButtonType.OK);
				alert.showAndWait();
				Main.setModifiedData(false);
				Main.showManageView();
			}
		} catch (ConnectionException cEx) {
			Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
			alert.showAndWait();
		}

	}
	
	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}

	}

}
