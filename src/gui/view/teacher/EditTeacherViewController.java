package gui.view.teacher;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import gui.Main;
import gui.view.Controller;
import gui.view.SelectorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;
import model.Profesor;

public class EditTeacherViewController extends SelectorController<Aula> {

	@FXML
	private TextField nombre;
	@FXML
	private TextField primerApellido;
	@FXML
	private TextField segundoApellido;
	@FXML
	private TextField NIF;
	@FXML
	private TextArea description;
	
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

	public void setTeacherAndClassrooms(Profesor tea, List<Aula> allClassrooms) {
		this.tea = tea;
		this.listAllClassrooms = allClassrooms;
		this.fillFields();
	}

	@FXML
	private void fillFields() {
	
		if(nombre != null)this.nombre.setText(tea.getNombre());
		this.primerApellido.setText(tea.getApellido1());
		this.segundoApellido.setText(tea.getApellido2());
		this.NIF.setText(tea.getNif());
		this.description.setText(this.tea.getNotas());
		
		
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
	private void acept() {
		System.out.println("Aceptar y sobreescibir los cambios del profesor.");
	}
	
	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}

	}

}
