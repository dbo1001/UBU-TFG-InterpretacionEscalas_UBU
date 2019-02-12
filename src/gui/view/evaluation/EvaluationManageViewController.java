package gui.view.evaluation;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import gui.Controller;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Alumno;
import model.Evaluacion;

/**
 * Controlador de la pantalla que lista las evaluaciones de un alumno
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class EvaluationManageViewController extends Controller {

	@FXML
	private TableView<CeldaEvaluacion> table;
	@FXML
	private TableColumn<CeldaEvaluacion, String> dateColumn;
	@FXML
	private TableColumn<CeldaEvaluacion, Label> editColumn;
	@FXML
	private TableColumn<CeldaEvaluacion, Label> deleteColumn;
	@FXML
	private Text name;
	@FXML
	private DatePicker from;
	@FXML
	private DatePicker to;

	private Set<Evaluacion> allEvaluations;
	private Alumno stu;

	/**
	 * Inicializa los elementos gráficos
	 */
	@FXML
	private void initialize() {
		dateColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, String>("Fecha"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, Label>("delete"));
		table.setPlaceholder(
				new Label("No se han encontrado evaluaciones. Agrega una nueva o revisa los filtros aplicados."));
	}

	/**
	 * Muestra la pantalla de creación de evaluaciones
	 * @throws IOException archino no encontrado
	 */
	@FXML
	private void addNewEvaluation() throws IOException {
		Main.showEvaluationView(stu);
	}

	/**
	 * Limpia los filtros
	 */
	@FXML
	private void clearFilters() {
		this.from.setValue(null);
		this.to.setValue(null);
		this.filter();
	}

	/**
	 * Filtra las evaluaciones por fecha
	 */
	@FXML
	private void filter() {
		if (this.from.getValue() != null && this.to.getValue() != null) {
			if (this.from.getValue().isAfter(this.to.getValue()) || this.from.getValue().equals(this.to.getValue())) {
				Alert alert = new Alert(AlertType.ERROR,
						"La fecha final debe ser posterior a la fecha inicial del filtro.");
				alert.showAndWait();
				this.from.setValue(null);
				this.to.setValue(null);
			} else {
				LocalDate ldFrom = this.from.getValue();
				LocalDate ldTo = this.to.getValue();
				long millisFrom = ldFrom.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
				long millisTo = ldTo.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
				Timestamp tsFrom = new Timestamp(millisFrom);
				Timestamp tsTo = new Timestamp(millisTo);
				this.table.getItems().clear();
				for (Evaluacion eva : this.allEvaluations) {
					if (eva.getFecha().after(tsFrom) && eva.getFecha().before(tsTo)) {
						this.table.getItems().add(new CeldaEvaluacion(eva));
					}
				}
			}
		}else {
			ObservableList<CeldaEvaluacion> cellsList = FXCollections.observableArrayList();
			for (Evaluacion eva : this.allEvaluations) {
				cellsList.add(new CeldaEvaluacion(eva));
			}
			table.getItems().setAll(cellsList);
		}
	}

	/**
	 * Inicializa los datos del alumno al que pertenecen las evaluaciones
	 * @param stu alumno al que pertenecen las evaluaciones
	 */
	public void setStudent(Alumno stu) {
		this.stu = stu;
		this.allEvaluations = new HashSet<Evaluacion>(stu.getEvaluacions());
		this.name.setText("Estas viendo las evaluaciones del alumno: " + this.stu.getApellido1() + " "
				+ this.stu.getApellido2() + ", " + this.stu.getNombre());
		ObservableList<CeldaEvaluacion> cellsList = FXCollections.observableArrayList();
		for (Evaluacion eva : this.allEvaluations) {
			cellsList.add(new CeldaEvaluacion(eva));
		}
		table.getItems().setAll(cellsList);
	}

	/**
	 * Subclase para formatear las celdas que muestran las evaluaciones
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	protected class CeldaEvaluacion {

		private Evaluacion eva;
		private Label edit;
		private Label delete;

		public CeldaEvaluacion(Evaluacion eva) {
			this.eva = eva;

			edit = new Label("Editar");
			edit.getStyleClass().add("controlLabel");
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditEvaluationView(eva);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
						e1.printStackTrace();
					}
				};

			});
			/*
			delete = new Label("Borrar");
			delete.getStyleClass().add("controlLabel");
			delete.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {

						Main.deleteEvaluation(eva);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
						e1.printStackTrace();
					}
				}

			});*/
		}

		/**
		 * Getter
		 * @return fecha
		 */
		public String getFecha() {
			return this.eva.getFecha().toString().substring(0, 16);
		}

		/**
		 * Getter
		 * @return label para editar evaluaciones
		 */
		public Label getEdit() {
			return edit;
		}

		/**
		 * Getter
		 * @return label para borrar evaluaciones
		 */
		public Label getDelete() {
			return delete;
		}

	}

}
