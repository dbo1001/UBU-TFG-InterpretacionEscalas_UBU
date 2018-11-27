package gui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Alumno;

public class ManageViewController extends Controller {

	@FXML
	private TableView<CeldaAlumno> table;
	@FXML
	private TableColumn<CeldaAlumno, String> nameColumn;
	@FXML
	private TableColumn<CeldaAlumno, String> surname1Column;
	@FXML
	private TableColumn<CeldaAlumno, String> surname2Column;
	@FXML
	private TableColumn<CeldaAlumno, Label> editColumn;
	@FXML
	private TableColumn<CeldaAlumno, Label> deleteColumn;

	@FXML
	private Label nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL, nM, nN, n�, nO, nP, nQ, nR, nS, nT, nU, nV, nW, nX,
			nY, nZ;
	@FXML
	private Label sA, sB, sC, sD, sE, sF, sG, sH, sI, sJ, sK, sL, sM, sN, s�, sO, sP, sQ, sR, sS, sT, sU, sV, sW, sX,
			sY, sZ;

	private Label currentNameFilter = null;
	private Label currentSurnameFilter = null;
	private List<Alumno> allStudents;

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("nombre"));
		surname1Column.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("apellido1"));
		surname2Column.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, String>("apellido2"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaAlumno, Label>("delete"));
	}

	@FXML
	private void addNewStudent() throws IOException {
		Main.showStudentView();
	}

	private void updateNameFilter(Label newFilter) {
		if(this.currentNameFilter != null) {
			this.currentNameFilter.setDisable(false);	
		}
		this.currentNameFilter = newFilter;
		if (newFilter != null) {
			newFilter.setDisable(true);
		}
		this.filter();
	}

	public void setAllStudents(List<Alumno> students) {
		this.allStudents = students;
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
		if (this.currentNameFilter != null) {
			List<Alumno> filteredStudents = new ArrayList<Alumno>();
			for (Alumno stu : this.allStudents) {
				if (stu.getNombre().charAt(0) == this.currentNameFilter.getText().charAt(0)) {
					filteredStudents.add(stu);
				}
			}
			this.loadStudents(filteredStudents);
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
	private void filterN�() {
		this.updateNameFilter(n�);
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

	/////////////////////////////// 7
	public class CeldaAlumno {

		private String nombre = "";
		private String apellido1 = "";
		private String apellido2 = "";
		private Label edit;
		private Label delete;
		private final EventHandler<MouseEvent> mouseOver = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.handCursor();
			}
		};
		private final EventHandler<MouseEvent> mouseLeft = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.defaultCursor();
			}
		};

		public CeldaAlumno(Alumno stu) {
			this.nombre = stu.getNombre();
			this.apellido1 = stu.getApellido1();
			this.apellido2 = stu.getApellido2();

			edit = new Label("Editar");
			// edit.setFont(new Font(18));
			edit.setTextFill(Color.web("3366bb"));
			edit.setUnderline(true);
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditStudentView(stu);
					} catch (IOException e1) {
						System.err.println("Error, archivo EditStudentView.fxml no encontrado en la carpeta view.");
						e1.printStackTrace();
					}
				};

			});
			edit.setOnMouseEntered(mouseOver);
			edit.setOnMouseExited(mouseLeft);

			delete = new Label("Borrar");
			// delete.setFont(new Font(18));
			delete.setTextFill(Color.web("3366bb"));
			delete.setUnderline(true);
			delete.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					Main.deleteStudent(stu);
				}

			});
			delete.setOnMouseEntered(mouseOver);
			delete.setOnMouseExited(mouseLeft);
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido1() {
			return apellido1;
		}

		public void setApellido1(String apellido1) {
			this.apellido1 = apellido1;
		}

		public String getApellido2() {
			return apellido2;
		}

		public void setApellido2(String apellido2) {
			this.apellido2 = apellido2;
		}

		public Label getEdit() {
			return edit;
		}

		public void setEdit(Label edit) {
			this.edit = edit;
		}

		public Label getDelete() {
			return delete;
		}

		public void setDelete(Label delete) {
			this.delete = delete;
		}

	}

}
