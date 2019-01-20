package gui.view.graphs;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.Alumno;
import model.Evaluacion;

public class EvaluationSelectionViewController extends Controller {

	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;
	@FXML
	private ListView<Evaluacion> evaluationLV;

	private List<Alumno> students;
	private List<Evaluacion> evaluations = new ArrayList<Evaluacion>();
	private final Callback<ListView<Evaluacion>, ListCell<Evaluacion>> callback = new Callback<ListView<Evaluacion>, ListCell<Evaluacion>>() {

		@Override
		public ListCell<Evaluacion> call(ListView<Evaluacion> evaluations) {
			ListCell<Evaluacion> cellsList = new ListCell<Evaluacion>() {
				@Override
				protected void updateItem(Evaluacion eva, boolean empty) {
					super.updateItem(eva, empty);
					if (eva != null) {
						setText(eva.getAlumno().getApellido1() + " " + eva.getAlumno().getApellido2() + ", "
								+ eva.getAlumno().getNombre() + "  ->  " + eva.getFecha().toString().substring(0, 16));
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
		this.evaluationLV.setCellFactory(callback);
		this.evaluationLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	private void select() {
		if (this.from.getValue() != null && this.to.getValue() != null) {
			if (this.from.getValue().isAfter(this.to.getValue())) {
				Alert alert = new Alert(AlertType.ERROR, "La fecha inicial debe ser anterior a la fecha final.",
						ButtonType.OK);
				alert.showAndWait();
			} else {
				LocalDate f = this.from.getValue();
				LocalDate t = this.to.getValue();
				Date fromDate = Date.from(Instant.from(f.atStartOfDay(ZoneId.systemDefault())));
				Date toDate = Date.from(Instant.from(t.atStartOfDay(ZoneId.systemDefault())));
				this.evaluationLV.getItems().clear();
				for (Evaluacion eva : this.evaluations) {
					if (eva.getFecha().after(fromDate) && eva.getFecha().before(toDate)) {
						this.evaluationLV.getItems().add(eva);
					}
				}
				this.sortEvaluations();
			}
		}
	}
	
	@FXML
	private void next() throws IOException {
		if(this.evaluationLV.getItems().size() > 0) {
			Main.showGraphSelectionView(this.evaluationLV.getItems());
		}else {
			Alert alert = new Alert(AlertType.INFORMATION, "Debes haber al menos 1 evaluacion seleccionada antes de continuar.");
			alert.setTitle("Alerta");
			alert.setHeaderText("");
			alert.show();
		}
	}
	
	@FXML
	private void delete() {
		this.evaluationLV.getItems().removeAll(this.evaluationLV.getSelectionModel().getSelectedItems());
	}
	
	private void sortEvaluations() {
		Collections.sort(this.evaluationLV.getItems(), new SortEvaluation());
	}

	public void setStudents(List<Alumno> students) {
		this.students = students;
		for (Alumno stu : this.students) {
			this.evaluations.addAll(stu.getEvaluacions());
		}
	}
	
	private class SortEvaluation implements Comparator<Evaluacion> {
		@Override
		public int compare(Evaluacion e1, Evaluacion e2) {
			if(e1.getFecha().before(e2.getFecha())) {
				return -1;
			}else if(e2.getFecha().before(e1.getFecha())) {
				return 1;
			}else {
				return 0;
			}
		}

	}

}
