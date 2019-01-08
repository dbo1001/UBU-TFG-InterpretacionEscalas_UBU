package gui.view.classroom;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import gui.Main;
import gui.view.SelectorController;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.Profesor;

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
	
	
	@FXML
	private void cancel() throws IOException {
		if(cancelAlert()) {
			Main.setModifiedData(false);
			super.goBack();
		}
		
	}

	@FXML
	private void acept() {
		System.out.println("Aceptar y agregar aula.");
	}
	
	public void setTeachers(List<Profesor> allTeachers) {
		super.initialize(callback, allTeachers, new SortTeacher());
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
