package gui.view.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

public class TeacherViewController extends Controller {

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
	private ListView<Aula> displayClassrooms;
	@FXML
	private ListView<Aula> selectedClassrooms;
	private List<Aula> listAllClassrooms;
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
	public void initialize() {
		this.displayClassrooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.displayClassrooms.setCellFactory(callback);
		this.selectedClassrooms.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.selectedClassrooms.setCellFactory(callback);
	}

	@FXML
	private void select() {
		this.selectedClassrooms.getItems().addAll(this.displayClassrooms.getSelectionModel().getSelectedItems());
		this.displayClassrooms.getItems().removeAll(this.displayClassrooms.getSelectionModel().getSelectedItems());
		this.sortClassrooms();
	}

	@FXML
	private void deselect() {
		this.displayClassrooms.getItems().addAll(this.selectedClassrooms.getSelectionModel().getSelectedItems());
		this.selectedClassrooms.getItems().removeAll(this.selectedClassrooms.getSelectionModel().getSelectedItems());
		this.sortClassrooms();
	}

	@FXML
	private void cancel() throws IOException {
		if (cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}

	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar profesor.");
	}

	public void setClassrooms(List<Aula> allClassrooms) {
		this.listAllClassrooms = allClassrooms;
		Collections.sort(listAllClassrooms, new SortClassroom());
		this.displayClassrooms.getItems().addAll(listAllClassrooms);
	}

	private void sortClassrooms() {
		Collections.sort(this.displayClassrooms.getItems(), new SortClassroom());
		Collections.sort(this.selectedClassrooms.getItems(), new SortClassroom());
	}

	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}

	}

}
