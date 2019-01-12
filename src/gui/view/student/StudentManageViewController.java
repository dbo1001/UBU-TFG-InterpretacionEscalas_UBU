package gui.view.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Alumno;

public class StudentManageViewController extends Controller {

	@FXML
	private TableView<CeldaAlumno> table;
	@FXML
	private TableColumn<CeldaAlumno, String> nameColumn;
	@FXML
	private TableColumn<CeldaAlumno, String> surname1Column;
	@FXML
	private TableColumn<CeldaAlumno, String> surname2Column;
	@FXML
	private TableColumn<CeldaAlumno, Label> showEvaluationColumn;
	@FXML
	private TableColumn<CeldaAlumno, Label> editColumn;
	@FXML
	private TableColumn<CeldaAlumno, Label> deleteColumn;

	@FXML
	private Label nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL, nM, nN, nÑ, nO, nP, nQ, nR, nS, nT, nU, nV, nW, nX,
			nY, nZ;
	@FXML
	private Label sA, sB, sC, sD, sE, sF, sG, sH, sI, sJ, sK, sL, sM, sN, sÑ, sO, sP, sQ, sR, sS, sT, sU, sV, sW, sX,
			sY, sZ;

	private Label currentNameFilter = null;
	private Label currentSurnameFilter = null;
	private List<Alumno> allStudents;

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("nombre"));
		surname1Column.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("apellido1"));
		surname2Column.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("apellido2"));
		showEvaluationColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, Label>("showEvaluation"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, Label>("delete"));
		table.setPlaceholder(new Label("No se han encontrado alumnos. Revisa los filtros aplicados."));
	}

	@FXML
	private void addNewStudent() throws IOException {
		Main.showStudentView();
	}

	private void updateNameFilter(Label newFilter) {
		if (this.currentNameFilter != null) {
			this.currentNameFilter.setDisable(false);
		}
		this.currentNameFilter = newFilter;
		if (newFilter != null) {
			newFilter.setDisable(true);
		}
		this.filter();
	}

	private void updateSurnameFilter(Label newFilter) {
		if (this.currentSurnameFilter != null) {
			this.currentSurnameFilter.setDisable(false);
		}
		this.currentSurnameFilter = newFilter;
		if (newFilter != null) {
			newFilter.setDisable(true);
		}
		this.filter();
	}

	public void setAllStudents(List<Alumno> students) {
		this.allStudents = students;
		Collections.sort(this.allStudents, new SortStudent());
		this.loadStudents(students);
	}

	private void loadStudents(List<Alumno> students) {
		ObservableList<CeldaAlumno> cellsList = FXCollections.observableArrayList();
		for (Alumno stu : students) {
			cellsList.add(new CeldaAlumno(stu));
		}
		table.getItems().setAll(cellsList);
	}

	private void filter() {
		this.loadCursor();
		List<Alumno> filteredStudents = new ArrayList<Alumno>();

		if (this.currentNameFilter != null && this.currentSurnameFilter != null) {
			for (Alumno stu : this.allStudents) {
				if (stu.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0) && stu
						.getApellido1().toUpperCase().charAt(0) == this.currentSurnameFilter.getText().charAt(0)) {
					filteredStudents.add(stu);
				}
			}
			this.loadStudents(filteredStudents);

		} else if (this.currentNameFilter != null && this.currentSurnameFilter == null) {
			for (Alumno stu : this.allStudents) {
				if (stu.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0)) {
					filteredStudents.add(stu);
				}
			}
			this.loadStudents(filteredStudents);

		} else if (this.currentNameFilter == null && this.currentSurnameFilter != null) {
			for (Alumno stu : this.allStudents) {
				if (stu.getApellido1().toUpperCase().charAt(0) == this.currentSurnameFilter.getText().charAt(0)) {
					filteredStudents.add(stu);
				}
			}
			this.loadStudents(filteredStudents);

		} else {
			this.loadStudents(allStudents);
		}
		this.defaultCursor();
	}

	@FXML
	private void clearFilters() {
		this.updateNameFilter(null);
		this.updateSurnameFilter(null);
		this.filter();
	}

	///////////////////////////////
	protected class CeldaAlumno {

		private Alumno stu;
		private Label showEvaluation;
		private Label edit;
		private Label delete;

		public CeldaAlumno(Alumno stu) {
			this.stu = stu;

			showEvaluation = new Label("Ver evaluaciones");
			showEvaluation.getStyleClass().add("controlLabel");
			showEvaluation.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEvaluationManageView(stu);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
						e1.printStackTrace();
					}
				};

			});

			edit = new Label("Editar");
			edit.getStyleClass().add("controlLabel");
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditStudentView(stu);
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
						Main.deleteStudent(stu);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
						e1.printStackTrace();
					}
				}

			});
		}

		public String getNombre() {
			return this.stu.getNombre();
		}

		public String getApellido1() {
			return this.stu.getApellido1();
		}

		public String getApellido2() {
			return this.stu.getApellido2();
		}
		
		public Label getShowEvaluation() {
			return showEvaluation;
		}

		public Label getEdit() {
			return edit;
		}

		public Label getDelete() {
			return delete;
		}
	}

	private class SortStudent implements Comparator<Alumno> {
		@Override
		public int compare(Alumno a1, Alumno a2) {
			String a1Display = "" + a1.getApellido1() + " " + a1.getApellido2() + ", " + a1.getNombre();
			String a2Display = "" + a2.getApellido1() + " " + a2.getApellido2() + ", " + a2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(a1Display, a2Display);
		}

	}

	@FXML
	private void filterNA() {
		this.updateNameFilter(nA);
	}

	@FXML
	private void filterNB() {
		this.updateNameFilter(nB);
	}

	@FXML
	private void filterNC() {
		this.updateNameFilter(nC);
	}

	@FXML
	private void filterND() {
		this.updateNameFilter(nD);
	}

	@FXML
	private void filterNE() {
		this.updateNameFilter(nE);
	}

	@FXML
	private void filterNF() {
		this.updateNameFilter(nF);
	}

	@FXML
	private void filterNG() {
		this.updateNameFilter(nG);
	}

	@FXML
	private void filterNH() {
		this.updateNameFilter(nH);
	}

	@FXML
	private void filterNI() {
		this.updateNameFilter(nI);
	}

	@FXML
	private void filterNJ() {
		this.updateNameFilter(nJ);
	}

	@FXML
	private void filterNK() {
		this.updateNameFilter(nK);
	}

	@FXML
	private void filterNL() {
		this.updateNameFilter(nL);
	}

	@FXML
	private void filterNM() {
		this.updateNameFilter(nM);
	}

	@FXML
	private void filterNN() {
		this.updateNameFilter(nN);
	}

	@FXML
	private void filterNÑ() {
		this.updateNameFilter(nÑ);
	}

	@FXML
	private void filterNO() {
		this.updateNameFilter(nO);
	}

	@FXML
	private void filterNP() {
		this.updateNameFilter(nP);
	}

	@FXML
	private void filterNQ() {
		this.updateNameFilter(nQ);
	}

	@FXML
	private void filterNR() {
		this.updateNameFilter(nR);
	}

	@FXML
	private void filterNS() {
		this.updateNameFilter(nS);
	}

	@FXML
	private void filterNT() {
		this.updateNameFilter(nT);
	}

	@FXML
	private void filterNU() {
		this.updateNameFilter(nU);
	}

	@FXML
	private void filterNV() {
		this.updateNameFilter(nV);
	}

	@FXML
	private void filterNW() {
		this.updateNameFilter(nW);
	}

	@FXML
	private void filterNX() {
		this.updateNameFilter(nX);
	}

	@FXML
	private void filterNY() {
		this.updateNameFilter(nY);
	}

	@FXML
	private void filterNZ() {
		this.updateNameFilter(nZ);
	}

	@FXML
	private void filterSA() {
		this.updateSurnameFilter(sA);
	}

	@FXML
	private void filterSB() {
		this.updateSurnameFilter(sB);
	}

	@FXML
	private void filterSC() {
		this.updateSurnameFilter(sC);
	}

	@FXML
	private void filterSD() {
		this.updateSurnameFilter(sD);
	}

	@FXML
	private void filterSE() {
		this.updateSurnameFilter(sE);
	}

	@FXML
	private void filterSF() {
		this.updateSurnameFilter(sF);
	}

	@FXML
	private void filterSG() {
		this.updateSurnameFilter(sG);
	}

	@FXML
	private void filterSH() {
		this.updateSurnameFilter(sH);
	}

	@FXML
	private void filterSI() {
		this.updateSurnameFilter(sI);
	}

	@FXML
	private void filterSJ() {
		this.updateSurnameFilter(sJ);
	}

	@FXML
	private void filterSK() {
		this.updateSurnameFilter(sK);
	}

	@FXML
	private void filterSL() {
		this.updateSurnameFilter(sL);
	}

	@FXML
	private void filterSM() {
		this.updateSurnameFilter(sM);
	}

	@FXML
	private void filterSN() {
		this.updateSurnameFilter(sN);
	}

	@FXML
	private void filterSÑ() {
		this.updateSurnameFilter(sÑ);
	}

	@FXML
	private void filterSO() {
		this.updateSurnameFilter(sO);
	}

	@FXML
	private void filterSP() {
		this.updateSurnameFilter(sP);
	}

	@FXML
	private void filterSQ() {
		this.updateSurnameFilter(sQ);
	}

	@FXML
	private void filterSR() {
		this.updateSurnameFilter(sR);
	}

	@FXML
	private void filterSS() {
		this.updateSurnameFilter(sS);
	}

	@FXML
	private void filterST() {
		this.updateSurnameFilter(sT);
	}

	@FXML
	private void filterSU() {
		this.updateSurnameFilter(sU);
	}

	@FXML
	private void filterSV() {
		this.updateSurnameFilter(sV);
	}

	@FXML
	private void filterSW() {
		this.updateSurnameFilter(sW);
	}

	@FXML
	private void filterSX() {
		this.updateSurnameFilter(sX);
	}

	@FXML
	private void filterSY() {
		this.updateSurnameFilter(sY);
	}

	@FXML
	private void filterSZ() {
		this.updateSurnameFilter(sZ);
	}

}
