package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import connection.ConnectionException;
import connection.manageService.ClassroomServiceImpl;
import connection.manageService.EvaluationServiceImpl;
import connection.manageService.ManageService;
import connection.manageService.StudentServiceImpl;
import connection.manageService.TeacherServiceImpl;
import connection.utilService.UtilService;
import connection.utilService.UtilServiceImpl;
import gui.view.MainViewController;
import gui.view.classroom.ClassroomManageViewController;
import gui.view.classroom.ClassroomViewController;
import gui.view.classroom.EditClassroomViewController;
import gui.view.evaluation.EditEvaluationViewController;
import gui.view.evaluation.EvaluationManageViewController;
import gui.view.evaluation.EvaluationViewController;
import gui.view.student.EditStudentViewController;
import gui.view.student.StudentManageViewController;
import gui.view.student.StudentViewController;
import gui.view.teacher.EditTeacherViewController;
import gui.view.teacher.TeacherManageViewController;
import gui.view.teacher.TeacherViewController;
import io.IOControl;
import io.IOControlImpl;
import gui.view.graphs.EvaluationSelectionViewController;
import gui.view.graphs.GraphSelectionViewController;
import gui.view.graphs.GraphViewController;
import gui.view.graphs.StudentSelectionViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import model.Alumno;
import model.Areafuncional;
import model.Aula;
import model.Categorizacion;
import model.Evaluacion;
import model.Item;
import model.Profesor;
import model.Puntuacion;

/**
 * Main de la aplicación
 * 
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static ManageService<Alumno, String> studentService = new StudentServiceImpl();
	private static ManageService<Aula, String> classroomService = new ClassroomServiceImpl();
	private static ManageService<Profesor, String> teacherService = new TeacherServiceImpl();
	private static ManageService<Evaluacion, Timestamp> evaluationService = new EvaluationServiceImpl();
	private static UtilService utilService = new UtilServiceImpl();
	private static MainViewController mvC;
	private static boolean modifiedData = false;
	private static Profesor currentTeacher;
	private static LinkedList<Node> previousNodeQueue = new LinkedList<Node>();
	private final static String PATH_LOCAL = "iodata/";
	private final static String PATH_EVALUATIONS = "ioData/Evaluaciones/";
	private final static Image icon = new Image("/res/icon.png");

	/**
	 * Método main que pone en marcha la aplicación
	 * 
	 * @param args argumentos
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Método que muestra la primera pantalla
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Interpretación de escalas");
		Image icon = new Image("/res/icon.png");

		primaryStage.getIcons().add(icon);

		showMain();
		showLogInView();
	}

	/**
	 * Carga la escena principal
	 * 
	 * @throws IOException archivo no encontrado
	 */
	private void showMain() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainView.fxml"));
		Main.mainLayout = loader.load();
		Main.mvC = loader.getController();
		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().add(this.getClass().getResource("gui.css").toExternalForm());
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}

	/**
	 * Establece al profesor logeado como profesor actual
	 * 
	 * @param tea profesor que ha hecho log in
	 * @throws IOException archivo no encontrado
	 */
	public static void setCurrentTeacher(Profesor tea) throws IOException {
		Main.currentTeacher = tea;
		Main.mvC.setCurrentTeacher(Main.currentTeacher);
		Main.showManageView();
	}

	/**
	 * Muestra la pantalla de log in
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showLogInView() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/LogInView.fxml"));
		BorderPane logInView = loader.load();

		Main.mainLayout.setCenter(logInView);
		Main.primaryStage.show();
	}

	/**
	 * Muestra el menú principal, donde se listan los alumnos, profesores y aulas
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showManageView() throws IOException {

		Main.loadCursor();

		Main.previousNodeQueue.clear();

		Main.currentTeacher = Main.teacherService.getOne(Main.currentTeacher.getNif());

		// Pantalla general que contiene todas las pestañas
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ManageView.fxml"));
		TabPane manageView = loader.load();

		// Pestaña de los alumnos
		loader = new FXMLLoader(Main.class.getResource("view/student/StudentManageView.fxml"));
		BorderPane studentManageView = loader.load();
		StudentManageViewController sMVC = loader.getController();

		// Si somos administrador se muestran todos los alumnos, si no solo los de las
		// aulas del profesor
		if (!Main.currentTeacher.getPermisos()) {
			List<Alumno> currentStudents = new ArrayList<Alumno>();
			for (Aula cla : Main.currentTeacher.getAulas()) {
				currentStudents.addAll(cla.getAlumnos());
			}
			sMVC.setAllStudents(currentStudents);
		} else {
			sMVC.setAllStudents(Main.studentService.getAll());
		}

		((Tab) manageView.getTabs().get(0)).setContent(studentManageView);

		// Si somos administrador mostramos los profesores y aulas
		if (Main.currentTeacher.getPermisos()) {

			// Pestaña de los profesores
			loader = new FXMLLoader(Main.class.getResource("view/teacher/TeacherManageView.fxml"));
			BorderPane teacherManageView = loader.load();
			TeacherManageViewController tMVC = loader.getController();
			tMVC.setAllTeachers(teacherService.getAll());
			Tab tabTea = new Tab();
			tabTea.setText("Profesores");
			tabTea.setContent(teacherManageView);
			tabTea.setClosable(false);
			manageView.getTabs().add(tabTea);

			// Pestaña de las aulas
			loader = new FXMLLoader(Main.class.getResource("view/classroom/ClassroomManageView.fxml"));
			BorderPane classroomManageView = loader.load();
			ClassroomManageViewController cMVC = loader.getController();
			cMVC.setAllClassrooms(classroomService.getAll());
			Tab tabClass = new Tab();
			tabClass.setText("Aulas");
			tabClass.setContent(classroomManageView);
			tabClass.setClosable(false);
			manageView.getTabs().add(tabClass);

		}

		Main.mainLayout.setCenter(manageView);

		Main.defaultCursor();
	}

	/**
	 * Muestra la pantalla de creación de alumnos
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showStudentView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/StudentView.fxml"));
		BorderPane studentView = loader.load();
		StudentViewController sVC = loader.getController();
		sVC.setClassrooms(Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(studentView);
	}

	/**
	 * Muestra la pantalla de creción de profesores
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showTeacherView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/teacher/TeacherView.fxml"));
		BorderPane teacherView = loader.load();
		TeacherViewController tVC = loader.getController();
		tVC.setClassrooms(Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(teacherView);
	}

	/**
	 * Muestra la pantalla de creación de aulas
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showClassroomView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/classroom/ClassroomView.fxml"));
		BorderPane classroomView = loader.load();
		ClassroomViewController cVC = loader.getController();
		cVC.setTeachers(Main.teacherService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(classroomView);

	}

	/**
	 * Muestra la pantalla que lista las evaluaciones de un alumno
	 * 
	 * @param stu alumno del que se listarán las evaluaciones
	 * @throws IOException archivo no encontrado
	 */
	public static void showEvaluationManageView(Alumno stu) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/evaluation/EvaluationManageView.fxml"));
		BorderPane evaluationManageView = loader.load();

		EvaluationManageViewController eMVC = loader.getController();
		eMVC.setStudent(stu);

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(evaluationManageView);

	}

	/**
	 * Muestra la pantalla de creación de evaluaciones
	 * 
	 * @param stu alumno al que pertenece la evaluación
	 * @throws IOException archivo no encontrado
	 */
	public static void showEvaluationView(Alumno stu) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/evaluation/EvaluationView.fxml"));
		BorderPane evaluationView = loader.load();
		EvaluationViewController eVC = loader.getController();
		eVC.setData(stu, Main.utilService.getAllFunctionalAreas(), Main.utilService.getAllCategories(),
				Main.utilService.getAllItems());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(evaluationView);
	}

	/**
	 * Muestra la pantalla de selección de alumnos para generar un gráfico
	 * 
	 * @throws IOException archivo no encontrado
	 */
	public static void showStudentSelectionView() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/StudentSelectionView.fxml"));
		BorderPane studentSelectionView = loader.load();
		StudentSelectionViewController ssVC = loader.getController();

		if (Main.currentTeacher.getPermisos()) {
			ssVC.setClassrooms(Main.getClassroomService().getAll());
		} else {
			ssVC.setClassrooms(Main.currentTeacher.getAulas());
		}

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(studentSelectionView);
	}

	/**
	 * Muestra la pantalla de selección de evaluaciones para generar un gráfico
	 * 
	 * @param selectedStudents alumnos seleccionados en el paso anterior, las
	 *                         evaluaciones mostradas les pertenecen
	 * @throws IOException archivo no encontrado
	 */
	public static void showEvaluationSelectionView(List<Alumno> selectedStudents) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/EvaluationSelectionView.fxml"));
		BorderPane evaluationSelectionView = loader.load();
		EvaluationSelectionViewController esVC = loader.getController();
		esVC.setStudents(selectedStudents);

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(evaluationSelectionView);
	}

	/**
	 * Muestra la pantalla de selección de gráficos, en la que elegimos las
	 * características del gráfico a generar
	 * 
	 * @param selectedEvaluations evaluaciones eleccionadas en el paso anterior
	 * @throws IOException archivo no encontrado
	 */
	public static void showGraphSelectionView(List<Evaluacion> selectedEvaluations) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/GraphSelectionView.fxml"));
		BorderPane graphSelectionView = loader.load();
		GraphSelectionViewController gsVC = loader.getController();
		gsVC.setData(selectedEvaluations, Main.utilService.getAllFunctionalAreas());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(graphSelectionView);
	}

	/**
	 * Muestra el gráfico generado
	 * 
	 * @param selectedEvaluations evaluaciones seleccionadas anteriormente
	 * @param selectedFa          áreas funcionales seleccionadas en el paso
	 *                            anterior
	 * @param selectedCa          categorías seleccionadas en el paso anterior
	 * @param selectedIt          ítems seleccionados en el paso anterior
	 * @throws IOException archivo no encontrado
	 */
	public static void showGraphView(List<Evaluacion> selectedEvaluations, List<Areafuncional> selectedFa,
			List<Categorizacion> selectedCa, List<Item> selectedIt) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/GraphView.fxml"));
		BorderPane graphView = loader.load();
		GraphViewController gVC = loader.getController();

		if (selectedCa.size() == 0 && selectedIt.size() == 0) {
			gVC.faChart(selectedEvaluations, selectedFa);
		} else if (selectedIt.size() == 0) {
			gVC.caChart(selectedEvaluations, selectedCa);
		} else {
			gVC.itChart(selectedEvaluations, selectedIt);
		}

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(graphView);
	}

	/**
	 * Muestra la pantalla para editar alumnos
	 * 
	 * @param stu alumno a editar
	 * @throws IOException archivo no encontrado
	 */
	public static void showEditStudentView(Alumno stu) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/EditStudentView.fxml"));
		BorderPane editSudentView = loader.load();
		EditStudentViewController editStudentController = loader.getController();
		editStudentController.setStudentAndClassrooms(stu, Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(editSudentView);
		// Main.studentService.edit(stu.getId());

	}

	/**
	 * Muestra la pantalla para editar profesores
	 * 
	 * @param tea profesor a editar
	 * @throws IOException archivo no encontrado
	 */
	public static void showEditTeacherView(Profesor tea) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/teacher/EditTeacherView.fxml"));
		BorderPane editTeacherView = loader.load();
		EditTeacherViewController editTeacherController = loader.getController();
		editTeacherController.setTeacherAndClassrooms(tea, Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(editTeacherView);
		// Main.teacherService.edit(tea.getId());

	}

	/**
	 * Muestra la pantalla para editar aulas
	 * 
	 * @param cla aula a editar
	 * @throws IOException archivo no encontrado
	 */
	public static void showEditClassroomView(Aula cla) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/classroom/EditClassroomView.fxml"));
		BorderPane editClassroomView = loader.load();
		EditClassroomViewController editClassroomController = loader.getController();
		editClassroomController.setClassroom(cla, Main.teacherService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(editClassroomView);

	}

	/**
	 * Muestra la pantalla para editar evaluaciones
	 * 
	 * @param eva evaluación a editar
	 * @throws IOException archivo no encontrado
	 */
	public static void showEditEvaluationView(Evaluacion eva) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/evaluation/EditEvaluationView.fxml"));
		BorderPane editEvaluationView = loader.load();
		EditEvaluationViewController editEvaluationController = loader.getController();
		editEvaluationController.setData(eva, Main.utilService.getAllFunctionalAreas(),
				Main.getUtilService().getAllCategories(), Main.getUtilService().getAllItems());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(editEvaluationView);

	}

	/**
	 * Pregunta al usuario si quiere borrar el alumno y actúa en consecuencia
	 * 
	 * @param stu alumno a borrar
	 * @throws IOException archivo no encontrado
	 */
	public static void deleteStudent(Alumno stu) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar el alumno/a: " + stu.getNombre() + " " + stu.getApellido1() + " "
						+ stu.getApellido2() + "?\n" + "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			try {
				if (Main.studentService.delete(stu)) {
					alert = new Alert(AlertType.INFORMATION, "El alumno se ha borrado correctamente.");
					alert.initOwner(Main.getPrimaryStage());
					alert.showAndWait();
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				alert = new Alert(AlertType.ERROR, cEx.getError().getText());
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
			}
		}
	}

	/**
	 * Pregunta al usuario si quiere borrar el profesor y actúa en consecuencia
	 * 
	 * @param tea profesor a borrar
	 * @throws IOException archivo no encontrado
	 */
	public static void deleteTeacher(Profesor tea) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar el profesor/a: " + tea.getNombre() + " " + tea.getApellido1() + " "
						+ tea.getApellido2() + "?\n" + "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			try {
				if (Main.teacherService.delete(tea)) {
					alert = new Alert(AlertType.INFORMATION, "El profesor se ha borrado correctamente.");
					alert.initOwner(Main.getPrimaryStage());
					alert.showAndWait();
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				alert = new Alert(AlertType.ERROR, cEx.getError().getText());
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
			}
		}

	}

	/**
	 * Pregunta al usuario si quiere borrar el aula y actúa en consecuencia
	 * 
	 * @param cla aula a borrar
	 * @throws IOException archivo no encontrado
	 */
	public static void deleteClassroom(Aula cla) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que quieres borrar el aula: "
				+ cla.getNombre() + " ?\n" + "Los cambios serán definitivos.", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {

			try {
				if (Main.classroomService.delete(cla)) {
					alert = new Alert(AlertType.INFORMATION, "El aula se ha borrado correctamente.");
					alert.initOwner(Main.getPrimaryStage());
					alert.showAndWait();
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				alert = new Alert(AlertType.ERROR, cEx.getError().getText());
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
			}

		}

	}

	/**
	 * Pregunta al usuario si quiere borrar la evaluación y actúa en consecuencia
	 * 
	 * @param eva evaluación a borrar
	 * @throws IOException archivo no encontrado
	 */
	public static void deleteEvaluation(Evaluacion eva) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar la evaluación con fecha "
						+ eva.getFecha().toString().substring(0, 19) + " del alumno/a " + eva.getAlumno().getApellido1()
						+ " " + eva.getAlumno().getApellido2() + ", " + eva.getAlumno().getNombre() + " ?\n"
						+ "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {

			try {
				if (Main.evaluationService.delete(eva)) {
					alert = new Alert(AlertType.INFORMATION, "La evaluación se ha borrado correctamente.");
					alert.initOwner(Main.getPrimaryStage());
					alert.showAndWait();
					Main.showManageView();
				}
			} catch (ConnectionException cEx) {
				alert = new Alert(AlertType.ERROR, cEx.getError().getText());
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
			}

		}

	}

	/**
	 * Importa los datos de la nuve
	 * 
	 * @return éxito o fracaso
	 */
	public static boolean importData() {
		boolean result = false;
		List<Aula> currentCla;
		List<Alumno> errorStudents = new ArrayList<Alumno>();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(Main.getPrimaryStage());
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		IOControl io;

		// Si somos administrador se importan todas la aulas, si no solo las que tenemos
		// asignadas
		if (Main.currentTeacher.getPermisos()) {
			currentCla = Main.getClassroomService().getAll();
		} else {
			currentCla = Main.currentTeacher.getAulas();
		}

		io = new IOControlImpl(Main.studentService.getAll(), Main.teacherService.getAll(),
				Main.classroomService.getAll(), currentCla);

		try {
			alert.setTitle("Importando datos...");
			alert.show();

			if (!io.importData()) {
				return result;
			}

			alert.setContentText("Leyendo ficheros... ");
			List<Alumno> newStu = io.readStudentsCSV(PATH_LOCAL + "alumnos.csv");
			List<Profesor> newTea = io.readTeachersCSV(PATH_LOCAL + "profesores.csv");
			List<Aula> newCla = io.readClassroomsCSV(PATH_LOCAL + "aulas.csv");
			alert.close();
			alert.setContentText(
					alert.getContentText() + "OK\nActualizando base de datos...\n\tImportando profesores...");
			alert.show();

			for (Profesor tea : newTea) {
				Profesor oldTea = Main.teacherService.getOne(tea.getNif());
				if (oldTea != null) {
					tea.setId(oldTea.getId());
					Main.teacherService.edit(tea);
				} else {
					Profesor nuevoProfesor = new Profesor();
					nuevoProfesor.setApellido1(tea.getApellido1());
					nuevoProfesor.setApellido2(tea.getApellido2());
					nuevoProfesor.setContrasena(tea.getContrasena());
					nuevoProfesor.setNif(tea.getNif());
					nuevoProfesor.setNombre(tea.getNombre());
					nuevoProfesor.setNotas(tea.getNotas());
					nuevoProfesor.setPermisos(tea.getPermisos());
					Main.teacherService.add(nuevoProfesor);
				}
			}

			alert.close();
			alert.setContentText(alert.getContentText() + "OK\n\tImportando aulas...");
			alert.show();

			for (Aula cla : newCla) {
				Aula oldCla = Main.getClassroomService().getOne(cla.getNombre());
				List<Profesor> profesoresAsociados = new ArrayList<Profesor>();
				for (Profesor tea : cla.getProfesors()) {
					Profesor profesorAsociado = Main.getTeacherService().getOne(tea.getNif());
					if (profesorAsociado != null) {
						profesoresAsociados.add(profesorAsociado);
					}
				}
				if (oldCla != null) {
					cla.setId(oldCla.getId());
					cla.getProfesors().clear();
					cla.getProfesors().addAll(profesoresAsociados);
					Main.classroomService.edit(cla);
				} else {
					Aula nuevaAula = new Aula();
					nuevaAula.setCapacidad(cla.getCapacidad());
					nuevaAula.setNombre(cla.getNombre());
					nuevaAula.setNotas(cla.getNotas());
					nuevaAula.getProfesors().addAll(profesoresAsociados);
					Main.classroomService.add(nuevaAula);
				}
			}

			alert.close();
			alert.setContentText(alert.getContentText() + "OK\n\tImportando alumnos...");
			alert.show();

			for (Alumno stu : newStu) {
				try {
					Alumno oldStu = Main.studentService.getOne(stu.getCodigo());
					Aula aulaAsociada = Main.getClassroomService().getOne(stu.getAula().getNombre());
					if (oldStu != null) {
						stu.setId(oldStu.getId());
						stu.setAula(aulaAsociada);
						Main.studentService.edit(stu);
					} else {

						Alumno nuevoAlu = new Alumno();
						nuevoAlu.setNombre(stu.getNombre());
						nuevoAlu.setApellido1(stu.getApellido1());
						nuevoAlu.setApellido2(stu.getApellido2());
						nuevoAlu.setAula(aulaAsociada);
						nuevoAlu.setCodigo(stu.getCodigo());
						nuevoAlu.setDireccion(stu.getDireccion());
						nuevoAlu.setEvaluacions(stu.getEvaluacions());
						nuevoAlu.setFechaNacimiento(stu.getFechaNacimiento());
						nuevoAlu.setNotas(stu.getNotas());
						Main.studentService.add(nuevoAlu);
					}
				} catch (ConnectionException cex) {
					errorStudents.add(stu);
				}
			}
			
			if(errorStudents.size() > 0) {
				Alert errorStudentsAlert = new Alert(AlertType.ERROR);
				errorStudentsAlert.initOwner(getPrimaryStage());
				errorStudentsAlert.setTitle("Error");
				errorStudentsAlert.setHeaderText("Ha habído un error al importar los alumnos con los siguientes códigos en la nube:");
				for(Alumno stu : errorStudents) {
					errorStudentsAlert.setContentText(errorStudentsAlert.getContentText() + " " + stu.getCodigo() + ",");
				}
				errorStudentsAlert.showAndWait();
				
			}

			alert.close();
			alert.setContentText(alert.getContentText() + "OK\n\tImportando evaluaciones...");
			alert.show();

			for (Aula cla : currentCla) {
				String PATH_CLA = PATH_EVALUATIONS + cla.getNombre() + "/";
				List<Evaluacion> newEva = io.readEvaluationsCSV(PATH_CLA + "evaluaciones.csv");
				List<Puntuacion> newPun = io.readPuntuationsCSV(PATH_CLA + "puntuaciones.csv");
				List<Puntuacion> puntuacionesClasificadas = new ArrayList<Puntuacion>();

				for (Evaluacion eva : newEva) {
					Evaluacion oldEva = Main.getEvaluationService().getOne(eva.getFecha());
					Evaluacion nuevaEvaluacion = new Evaluacion();
					Alumno alumnoAsociado = Main.getStudentService().getOne(eva.getAlumno().getCodigo());
					eva.setAlumno(alumnoAsociado);

					if (oldEva != null) {
						nuevaEvaluacion = eva;
						nuevaEvaluacion.getPuntuacions().clear();
						nuevaEvaluacion.setId(oldEva.getId());
					} else {
						nuevaEvaluacion.setFecha(eva.getFecha());
						nuevaEvaluacion.setAlumno(eva.getAlumno());
					}

					for (Puntuacion pun : newPun) {
						if (pun.getEvaluacion().getFecha().getTime() == eva.getFecha().getTime()) {
							if (oldEva != null && oldEva.getPuntuacions().contains(pun)) {
								Puntuacion oldPun = oldEva.getPuntuacions().get(oldEva.getPuntuacions().indexOf(pun));
								oldPun.setValoracion(pun.getValoracion());
								nuevaEvaluacion.getPuntuacions().add(oldPun);
							} else {
								Puntuacion nuevaPuntuacion = new Puntuacion();
								nuevaPuntuacion.setEvaluacion(nuevaEvaluacion);
								nuevaPuntuacion.setItem(pun.getItem());
								nuevaPuntuacion.setValoracion(pun.getValoracion());
								nuevaEvaluacion.getPuntuacions().add(nuevaPuntuacion);
								pun.setEvaluacion(nuevaEvaluacion);
							}

							puntuacionesClasificadas.add(pun);
						}
					}

					newPun.removeAll(puntuacionesClasificadas);
					puntuacionesClasificadas.clear();

					if (oldEva != null) {
						Main.evaluationService.edit(nuevaEvaluacion);
					} else {
						Main.evaluationService.add(nuevaEvaluacion);
					}
				}

			}

			alert.close();
			alert.setContentText(alert.getContentText() + "OK\nTodos los datos han sido importados con EXITO.");
			alert.show();
			result = true;
		} catch (FileNotFoundException ex) {
			Alert error = new Alert(AlertType.ERROR);
			error.initOwner(Main.getPrimaryStage());
			error.setTitle("Error, archivo no encontrado.");
			error.setContentText(
					"Alguno de los archivos no se han encontrado en el directorio \"/ioData\" dentro de la carpeta raiz de la aplicacion.\nEs posible que haya habído un error al descargar los archivos de la nube.");
			error.showAndWait();
			ex.printStackTrace();
		} catch (Exception ex2) {
			Alert error = new Alert(AlertType.ERROR);
			error.initOwner(Main.getPrimaryStage());
			error.setTitle("Error desconocido.");
			error.setContentText("Ha ocurrido un error desconocido al importar los datos.");
			error.showAndWait();
			ex2.printStackTrace();
		}

		return result;
	}

	/**
	 * Exporta los datos a la nube
	 */
	public static void exportData() {
		IOControl io;

		io = new IOControlImpl(Main.studentService.getAll(), Main.teacherService.getAll(),
				Main.classroomService.getAll(), Main.currentTeacher.getAulas());

		io.exportData();

	}

	/**
	 * Vuelve a la pantalla anterior
	 */
	public static void goBack() {
		Main.mainLayout.setCenter(Main.previousNodeQueue.pollLast());
	}

	/**
	 * Convierte el cursor a una mano
	 */
	public static void handCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.HAND);
	}

	/**
	 * Convierte el cursor al cursor por defecto
	 */
	public static void defaultCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}

	/**
	 * Convierte el cursor al cursor de carga
	 */
	public static void loadCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.WAIT);
	}

	/**
	 * Setter
	 * 
	 * @param dataStatus estado de los datos
	 */
	public static void setModifiedData(boolean dataStatus) {
		Main.modifiedData = dataStatus;
	}

	/**
	 * getter
	 * 
	 * @return estado de los datos, true si hay cambios sin guardar
	 */
	public static boolean getDataIntegrity() {
		return Main.modifiedData;
	}

	/**
	 * getter
	 * 
	 * @return variable que realiza las tansacciones de los alumnos
	 */
	public static ManageService<Alumno, String> getStudentService() {
		return studentService;
	}

	/**
	 * getter
	 * 
	 * @return variable que realiza las transacciones de las aulas
	 */
	public static ManageService<Aula, String> getClassroomService() {
		return classroomService;
	}

	/**
	 * getter
	 * 
	 * @return vatiable que realiza las transacciones de los profesores
	 */
	public static ManageService<Profesor, String> getTeacherService() {
		return teacherService;
	}

	/**
	 * getter
	 * 
	 * @return vatiable que realiza las transacciones de los evaluaciones
	 */
	public static ManageService<Evaluacion, Timestamp> getEvaluationService() {
		return evaluationService;
	}

	/**
	 * getter
	 * 
	 * @return variable para consultar tables que el usuario no puede modificar
	 */
	public static UtilService getUtilService() {
		return utilService;
	}

	/**
	 * getter
	 * 
	 * @return profesor logeado actualmente
	 */
	public static Profesor getCurrentTeacher() {
		return Main.currentTeacher;
	}

	/**
	 * Getter
	 * 
	 * @return icono de la aplicación
	 */
	public static Image getIcon() {
		return icon;
	}

	/**
	 * Getter
	 * 
	 * @return escena principal
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
