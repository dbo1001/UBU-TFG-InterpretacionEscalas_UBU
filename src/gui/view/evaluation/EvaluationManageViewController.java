package gui.view.evaluation;

import java.io.IOException;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Alumno;
import model.Evaluacion;

public class EvaluationManageViewController extends Controller {

	@FXML
	private TableView<CeldaEvaluacion> table;
	@FXML
	private TableColumn<CeldaEvaluacion, String> dateColumn;
	@FXML
	private TableColumn<CeldaEvaluacion, Label> editColumn;
	@FXML
	private TableColumn<CeldaEvaluacion, Label> deleteColumn;

	private List<Evaluacion> allEvaluations;
	private Alumno stu;

	@FXML
	private void initialize() {
		dateColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, String>("Fecha"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaEvaluacion, Label>("delete"));
		table.setPlaceholder(new Label("No se han encontrado evaluaciones. Agrega una nueva o revisa los filtros aplicados."));
	}

	@FXML
	private void addNewEvaluation() throws IOException {
		Main.showEvaluationView(stu);
	}
	
	@FXML
	private void clearFilters() {
		//TODO
		/*this.updateNameFilter(null);
		this.updateSurnameFilter(null);
		this.filter();*/
	}

	public void setStudent(Alumno stu) {
		this.stu = stu;
		this.allEvaluations = stu.getEvaluacions();
		ObservableList<CeldaEvaluacion> cellsList = FXCollections.observableArrayList();
		for (Evaluacion eva : this.allEvaluations) {
			cellsList.add(new CeldaEvaluacion(eva));
		}
		table.getItems().setAll(cellsList);
	}

///////////////////////////////
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

			});
		}

		public String getFecha() {
			return this.eva.getFecha().toString();
		}

		public Label getEdit() {
			return edit;
		}

		public Label getDelete() {
			return delete;
		}

	}

}
