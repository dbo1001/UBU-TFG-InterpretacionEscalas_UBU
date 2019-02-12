package gui.view.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gui.Controller;
import gui.Main;
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
import javafx.scene.paint.Color;
import model.Profesor;

/**
 * Controlador de la pantalla que lista los profesores
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class TeacherManageViewController extends Controller {

	@FXML
	private TableView<CeldaProfesor> table;
	@FXML
	private TableColumn<CeldaProfesor, String> nameColumn;
	@FXML
	private TableColumn<CeldaProfesor, String> surname1Column;
	@FXML
	private TableColumn<CeldaProfesor, String> surname2Column;
	@FXML
	private TableColumn<CeldaProfesor, Label> editColumn;
	@FXML
	private TableColumn<CeldaProfesor, Label> deleteColumn;

	@FXML
	private Label nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL, nM, nN, nÑ, nO, nP, nQ, nR, nS, nT, nU, nV, nW, nX,
			nY, nZ;
	@FXML
	private Label sA, sB, sC, sD, sE, sF, sG, sH, sI, sJ, sK, sL, sM, sN, sÑ, sO, sP, sQ, sR, sS, sT, sU, sV, sW, sX,
			sY, sZ;

	private Label currentNameFilter = null;
	private Label currentSurnameFilter = null;
	private List<Profesor> allTeachers;

	/**
	 * Inicializa los elementos gráficos
	 */
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<CeldaProfesor, String>("nombre"));
		surname1Column.setCellValueFactory(new PropertyValueFactory<CeldaProfesor, String>("apellido1"));
		surname2Column.setCellValueFactory(new PropertyValueFactory<CeldaProfesor, String>("apellido2"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaProfesor, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaProfesor, Label>("delete"));
		table.setPlaceholder(new Label("No se han encontrado profesores. Revisa los filtros aplicados."));
	}

	/**
	 * Muestra la pantalla de creación de profesores
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void addNewTeacher() throws IOException {
		Main.showTeacherView();
	}

	/**
	 * Actualiza el filtro del nombre
	 * @param newFilter nuevo filtro
	 */
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

	/**
	 * Actualiza el filtro de los apellidos
	 * @param newFilter nuevo filtro
	 */
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

	/**
	 * Inicializa los datos de los profesores que serán listados en pantalla 
	 * @param teachers profesores
	 */
	public void setAllTeachers(List<Profesor> teachers) {
		this.allTeachers = teachers;
		Collections.sort(this.allTeachers, new SortTeacher());
		this.loadTeachers(teachers);
	}

	/**
	 * Carga los profesores en la tabla que los muestra
	 * @param teachers profesores
	 */
	private void loadTeachers(List<Profesor> teachers) {
		ObservableList<CeldaProfesor> cellsList = FXCollections.observableArrayList();
		for (Profesor tea : teachers) {
			cellsList.add(new CeldaProfesor(tea));
		}
		table.getItems().setAll(cellsList);
	}

	/**
	 * Filtra los profesores con los filtros seleccionados actualmente
	 */
	private void filter() {
		this.loadCursor();
		List<Profesor> filteredTeachers = new ArrayList<Profesor>();
		
		if (this.currentNameFilter != null && this.currentSurnameFilter != null) {
			for (Profesor tea : this.allTeachers) {
				if (tea.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0)
						&& tea.getApellido1().toUpperCase().charAt(0) == this.currentSurnameFilter.getText().charAt(0)) {
					filteredTeachers.add(tea);
				}
			}
			this.loadTeachers(filteredTeachers);
			
		}else if(this.currentNameFilter != null && this.currentSurnameFilter == null){
			for (Profesor tea : this.allTeachers) {
				if (tea.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0)){
					filteredTeachers.add(tea);
				}
			}
			this.loadTeachers(filteredTeachers);
			
		}else if(this.currentNameFilter == null && this.currentSurnameFilter != null){
			for (Profesor tea : this.allTeachers) {
				if (tea.getApellido1().toUpperCase().charAt(0) == this.currentSurnameFilter.getText().charAt(0)) {
					filteredTeachers.add(tea);
				}
			}
			this.loadTeachers(filteredTeachers);
			
		} else {
			this.loadTeachers(allTeachers);
		}
		this.defaultCursor();
	}

	/**
	 * Limpia los filtros
	 */
	@FXML
	private void clearFilters() {
		this.updateNameFilter(null);
		this.updateSurnameFilter(null);
		this.filter();
	}

	/**
	 * Subclase para formatear las celdas de la tabla que lista los profesores
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	protected class CeldaProfesor {

		private Profesor teacher;
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

		/**
		 * Constructor
		 * @param tea profesor mostrado en la celda
		 */
		public CeldaProfesor(Profesor tea) {
			this.teacher = tea;
			this.nombre = tea.getNombre();
			this.apellido1 = tea.getApellido1();
			this.apellido2 = tea.getApellido2();
			

			edit = new Label("Editar");
			// edit.setFont(new Font(18));
			edit.setTextFill(Color.web("3366bb"));
			edit.setUnderline(true);
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditTeacherView(teacher);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
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
					try {
						Main.deleteTeacher(teacher);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.showAndWait();
						e1.printStackTrace();
					}
				}

			});
			delete.setOnMouseEntered(mouseOver);
			delete.setOnMouseExited(mouseLeft);
			if(this.teacher.getNif().equals("00000000A")) {
				delete.setDisable(true);
			}
		}

		/**
		 * Getter
		 * @return nombre del profesor
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Getter
		 * @return apellido del profesor
		 */
		public String getApellido1() {
			return apellido1;
		}

		/**
		 * Getter
		 * @return segundo apellido del profesor
		 */
		public String getApellido2() {
			return apellido2;
		}

		/**
		 * Getter
		 * @return label para editar profesores
		 */
		public Label getEdit() {
			return edit;
		}

		/**
		 * Getter
		 * @return label para borrar profesores
		 */
		public Label getDelete() {
			return delete;
		}


	}
	
	/**
	 * Subclase para ordenar los profesores
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	private class SortTeacher implements Comparator<Profesor> {
		@Override
		public int compare(Profesor p1, Profesor p2) {
			String p1Display = "" + p1.getApellido1() + " " + p1.getApellido2() + ", " + p1.getNombre();
			String p2Display = "" + p2.getApellido1() + " " + p2.getApellido2() + ", " + p2.getNombre();
			return String.CASE_INSENSITIVE_ORDER.compare(p1Display, p2Display);
		}

	}

	/**
	 * Filtrar profesores cuyo nombre empieza por A
	 */
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

	/**
	 * Filtrar profesores cuyo apellido empieza por A
	 */
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
