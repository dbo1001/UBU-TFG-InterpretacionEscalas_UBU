package gui.view.classroom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import gui.view.teacher.TeacherManageViewController.CeldaProfesor;
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
import model.Aula;
import model.Profesor;

public class ClassroomManageViewController extends Controller {

	@FXML
	private TableView<CeldaAula> table;
	@FXML
	private TableColumn<CeldaAula, String> nameColumn;
	@FXML
	private TableColumn<CeldaAula, String> capacityColumn;
	@FXML
	private TableColumn<CeldaAula, Label> editColumn;
	@FXML
	private TableColumn<CeldaAula, Label> deleteColumn;

	@FXML
	private Label nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL, nM, nN, n—, nO, nP, nQ, nR, nS, nT, nU, nV, nW, nX,
			nY, nZ;

	private Label currentNameFilter = null;
	private List<Aula> allClassrooms;

	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, String>("nombre"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, String>("capacidad"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, Label>("delete"));
		table.setPlaceholder(new Label("No se han encontrado aulas. Revisa los filtros aplicados."));
	}

	@FXML
	private void addNewClassroom() throws IOException {
		Main.showClassroomView();
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

	public void setAllClassrooms(List<Aula> classrooms) {
		this.allClassrooms = classrooms;
		this.loadClassrooms(classrooms);
	}

	private void loadClassrooms(List<Aula> classrooms) {
		ObservableList<CeldaAula> cellsList = FXCollections.observableArrayList();
		for (Aula cla : classrooms) {
			cellsList.add(new CeldaAula(cla));
		}
		table.getItems().setAll(cellsList);
	}

	private void filter() {
		this.loadCursor();
		List<Aula> filteredClassrooms = new ArrayList<Aula>();
			
		if(this.currentNameFilter != null){
			for (Aula cla : this.allClassrooms) {
				if (cla.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0)){
					filteredClassrooms.add(cla);
				}
			}
			this.loadClassrooms(filteredClassrooms);
			
		} else {
			this.loadClassrooms(allClassrooms);
		}
		this.defaultCursor();
	}

	@FXML
	private void clearFilters() {
		this.updateNameFilter(null);
		this.filter();
	}

	///////////////////////////////
	public class CeldaAula {

		private String nombre = "";
		private String capacidad = "";
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

		public CeldaAula(Aula cla) {
			this.nombre = cla.getNombre();
			this.capacidad = "" + cla.getCapacidad();

			edit = new Label("Editar");
			// edit.setFont(new Font(18));
			edit.setTextFill(Color.web("3366bb"));
			edit.setUnderline(true);
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditClassroomView(cla);
					} catch (IOException e1) {
						System.err.println("Error, archivo EditClassroomView.fxml no encontrado en la carpeta view.");
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
					Main.deleteClassroom(cla);
				}

			});
			delete.setOnMouseEntered(mouseOver);
			delete.setOnMouseExited(mouseLeft);
		}

		public String getCapacidad() {
			return capacidad;
		}

		public void setCapacidad(String capacidad) {
			this.capacidad = capacidad;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellido1() {
			return capacidad;
		}

		public void setApellido1(String apellido1) {
			this.capacidad = apellido1;
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
	private void filterN—() {
		this.updateNameFilter(n—);
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

}
