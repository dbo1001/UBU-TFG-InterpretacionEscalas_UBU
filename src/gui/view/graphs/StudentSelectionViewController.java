package gui.view.graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import gui.view.SelectorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Alumno;
import model.Aula;

public class StudentSelectionViewController extends SelectorController<Alumno> {

	@FXML
	ComboBox<Aula> classroom;
	private final Callback<ListView<Alumno>, ListCell<Alumno>> callback = new Callback<ListView<Alumno>, ListCell<Alumno>>(){
		
		@Override
		public ListCell<Alumno> call(ListView<Alumno> students){
			ListCell<Alumno> cellsList = new ListCell<Alumno>() {
				
				@Override
				protected void updateItem(Alumno stu, boolean empty) {
					super.updateItem(stu, empty);
					if(stu != null) {
						setText("" + stu.getApellido1() + " " + stu.getApellido2() + ", " + stu.getNombre());
					}else {
						setText("");
					}
				}
			};
			return cellsList;
		}
	};
	
	@FXML
	private void initialize() {
		this.classroom.setConverter(new StringConverter<Aula>() {

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
	
	@FXML
	private void next() throws IOException {
		if(super.getSelectedObjects().size() > 0) {
			Main.showGraphSelectionView(super.getSelectedObjects());
		}else {
			Alert alert = new Alert(AlertType.INFORMATION, "Debes seleccionar almenos 1 alumno antes de continuar.");
			alert.setTitle("Alerta");
			alert.setHeaderText("");
			alert.show();
		}
	}
	
	@FXML
	private void switchDisplay() {
		super.getDisplayedObjects().clear();
		super.getDisplayedObjects().addAll(((Aula) this.classroom.getSelectionModel().getSelectedItem()).getAlumnos());
		super.sortObjects();
	}
	
	public void setClassrooms(List<Aula> classRooms) {
		List<Alumno> students = new ArrayList<Alumno>();
		students.addAll(classRooms.get(0).getAlumnos());
		super.initialize(callback, students, new SortStudent());
		this.classroom.getItems().addAll(classRooms);
		this.classroom.getSelectionModel().selectFirst();
	}
	
	private class SortStudent implements Comparator<Alumno>{
		@Override
		public int compare(Alumno a1, Alumno a2) {
			String a1Display = "" + a1.getApellido1() + " " + a1.getApellido2() + ", " + a1.getNombre();
			String a2Display = "" + a2.getApellido1() + " " + a2.getApellido2() + ", " + a2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(a1Display, a2Display);	
		}
		
	}
	
}
