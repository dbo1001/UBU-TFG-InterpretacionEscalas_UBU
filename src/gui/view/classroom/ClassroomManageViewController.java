package gui.view.classroom;

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
import model.Alumno;
import model.Aula;

/**
 * Controlador de la pantalla que lista las aulas
 * 
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class ClassroomManageViewController extends Controller {

	@FXML
	private TableView<CeldaAula> table;
	@FXML
	private TableColumn<CeldaAula, String> nameColumn;
	@FXML
	private TableColumn<CeldaAula, String> capacityColumn;
	@FXML
	private TableColumn<CeldaAula, Label> studentsColumn;
	@FXML
	private TableColumn<CeldaAula, Label> editColumn;
	@FXML
	private TableColumn<CeldaAula, Label> deleteColumn;

	@FXML
	private Label nA, nB, nC, nD, nE, nF, nG, nH, nI, nJ, nK, nL, nM, nN, nÑ, nO, nP, nQ, nR, nS, nT, nU, nV, nW, nX,
			nY, nZ;

	private Label currentNameFilter = null;
	private List<Aula> allClassrooms;

	/**
	 * Inicializa los datos
	 */
	@FXML
	private void initialize() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, String>("nombre"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, String>("capacidad"));
		studentsColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, Label>("checkStudents"));
		editColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, Label>("edit"));
		deleteColumn.setCellValueFactory(new PropertyValueFactory<CeldaAula, Label>("delete"));
		table.setPlaceholder(new Label("No se han encontrado aulas. Revisa los filtros aplicados."));
	}

	/**
	 * Muestra la pantalla de creación de aulas
	 * 
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void addNewClassroom() throws IOException {
		Main.showClassroomView();
	}

	/**
	 * Actualiza el filtro del nombre
	 * 
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
	 * Inicializa la pantalla con las aulas
	 * 
	 * @param classrooms todas las aulas actualmente en la base de datos
	 */
	public void setAllClassrooms(List<Aula> classrooms) {
		this.allClassrooms = classrooms;
		Collections.sort(this.allClassrooms, new SortClassroom());
		this.loadClassrooms(classrooms);
	}

	/**
	 * Lista las aulas
	 * 
	 * @param classrooms aulas a listar
	 */
	private void loadClassrooms(List<Aula> classrooms) {
		ObservableList<CeldaAula> cellsList = FXCollections.observableArrayList();
		for (Aula cla : classrooms) {
			cellsList.add(new CeldaAula(cla));
		}
		table.getItems().setAll(cellsList);
	}

	/**
	 * Filtra las aulas
	 */
	private void filter() {
		this.loadCursor();
		List<Aula> filteredClassrooms = new ArrayList<Aula>();

		if (this.currentNameFilter != null) {
			for (Aula cla : this.allClassrooms) {
				if (cla.getNombre().toUpperCase().charAt(0) == this.currentNameFilter.getText().charAt(0)) {
					filteredClassrooms.add(cla);
				}
			}
			this.loadClassrooms(filteredClassrooms);

		} else {
			this.loadClassrooms(allClassrooms);
		}
		this.defaultCursor();
	}

	/**
	 * Limpia los filtros
	 */
	@FXML
	private void clearFilters() {
		this.updateNameFilter(null);
		this.filter();
	}

	/**
	 * Subclase para dar formata a las celdas de la tabla que muestra las aulas
	 * 
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	protected class CeldaAula {

		@SuppressWarnings("unused")
		private Aula cla;
		private String nombre = "";
		private String capacidad = "";
		private Label checkStudents;
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
		 * 
		 * @param cla aula mostrada en esta celda
		 */
		public CeldaAula(Aula cla) {
			this.nombre = cla.getNombre();
			this.capacidad = "" + cla.getCapacidad();
			this.cla = cla;

			checkStudents = new Label("Consultar alumnos asignados");
			checkStudents.getStyleClass().add("controlLabel");
			checkStudents.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {

					Alert alert = new Alert(AlertType.INFORMATION,
							"Los siguiente alumnos pertenecen al aula " + cla.getNombre() + ":\n");
					alert.initOwner(Main.getPrimaryStage());
					for (Alumno stu : cla.getAlumnos()) {
						alert.setContentText(alert.getContentText() + "\n Nombre: " + stu.getApellido1() + " "
								+ stu.getApellido2() + ", " + stu.getNombre() + " 		Código: " + stu.getCodigo());
					}
					alert.setContentText(alert.getContentText() + "\n");
					alert.showAndWait();

				};

			});
			checkStudents.setOnMouseEntered(mouseOver);
			checkStudents.setOnMouseExited(mouseLeft);

			edit = new Label("Editar");
			// edit.setFont(new Font(18));
			edit.setTextFill(Color.web("3366bb"));
			edit.setUnderline(true);
			edit.getStyleClass().add("controlLabel");
			edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.showEditClassroomView(cla);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.initOwner(Main.getPrimaryStage());
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
			delete.getStyleClass().add("controlLabel");
			delete.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					try {
						Main.deleteClassroom(cla);
					} catch (IOException e1) {
						Alert alert = new Alert(AlertType.ERROR,
								"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
						alert.initOwner(Main.getPrimaryStage());
						alert.showAndWait();
						e1.printStackTrace();
					}
				}

			});
			delete.setOnMouseEntered(mouseOver);
			delete.setOnMouseExited(mouseLeft);
		}

		/**
		 * Getter
		 * 
		 * @return capacidad
		 */
		public String getCapacidad() {
			return capacidad;
		}

		/**
		 * Setter
		 * 
		 * @param capacidad capacidad
		 */
		public void setCapacidad(String capacidad) {
			this.capacidad = capacidad;
		}

		/**
		 * Getter
		 * 
		 * @return nombre
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Setter
		 * 
		 * @param nombre nombre
		 */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		/**
		 * Getter
		 * 
		 * @return primer apellido
		 */
		public String getApellido1() {
			return capacidad;
		}

		/**
		 * Setter
		 * 
		 * @param apellido1 primer apellido
		 */
		public void setApellido1(String apellido1) {
			this.capacidad = apellido1;
		}

		/**
		 * Getter
		 * 
		 * @return laber para comprobar los estudiantes del aula
		 */
		public Label getCheckStudents() {
			return checkStudents;
		}

		/**
		 * Setter
		 * 
		 * @param checkStudents laber para comprobar los estudiantes del aula
		 */
		public void setCheckStudents(Label checkStudents) {
			this.checkStudents = checkStudents;
		}

		/**
		 * Getter
		 * 
		 * @return label para editar aulas
		 */
		public Label getEdit() {
			return edit;
		}

		/**
		 * Setter
		 * 
		 * @param edit label para editar aulas
		 */
		public void setEdit(Label edit) {
			this.edit = edit;
		}

		/**
		 * Getter
		 * 
		 * @return label para borrar aulas
		 */
		public Label getDelete() {
			return delete;
		}

		/**
		 * Setter
		 * 
		 * @param delete label para borrar aulas
		 */
		public void setDelete(Label delete) {
			this.delete = delete;
		}

	}

	/**
	 * Subclase para ordenar aulas
	 * 
	 * @author Mario Núñez Izquierdo
	 * @version 1.0
	 *
	 */
	private class SortClassroom implements Comparator<Aula> {
		@Override
		public int compare(Aula a1, Aula a2) {
			return String.CASE_INSENSITIVE_ORDER.compare(a1.getNombre(), a2.getNombre());
		}
	}

	/**
	 * Filtra las aulas y muestra aquellas cuyo nombre empieza por A
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

}
