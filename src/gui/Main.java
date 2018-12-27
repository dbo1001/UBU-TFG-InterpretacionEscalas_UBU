package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import connection.manageService.ClassroomServiceImpl;
import connection.manageService.ManageService;
import connection.manageService.StudentServiceImpl;
import connection.manageService.TeacherServiceImpl;
import connection.utilService.UtilService;
import connection.utilService.UtilServiceImpl;
import gui.view.MainViewController;
import gui.view.classroom.ClassroomManageViewController;
import gui.view.classroom.EditClassroomViewController;
import gui.view.evaluation.EvaluationViewController;
import gui.view.student.EditStudentViewController;
import gui.view.student.StudentManageViewController;
import gui.view.student.StudentViewController;
import gui.view.teacher.EditTeacherViewController;
import gui.view.teacher.TeacherManageViewController;
import gui.view.teacher.TeacherViewController;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.Alumno;
import model.Areafuncional;
import model.Aula;
import model.Categorizacion;
import model.Item;
import model.Profesor;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static ManageService<Alumno> studentService = new StudentServiceImpl();
	private static ManageService<Aula> classroomService = new ClassroomServiceImpl();
	private static ManageService<Profesor> teacherService = new TeacherServiceImpl();
	private static UtilService utilService = new UtilServiceImpl();
	private static boolean modifiedData = false;
	//TODO borrar esta variable
	private static TeacherServiceImpl tS = new TeacherServiceImpl();
	private static Profesor currentTeacher = tS.getCurrentteacher();
	private static LinkedList<Node> previousNodeQueue = new LinkedList<Node>();

	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Interpretación de escalas");

		showMain();
		//showManageView();
		showStudentSelectionView();
	}

	private void showMain() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainView.fxml"));
		Main.mainLayout = loader.load();
		MainViewController mVC = loader.getController();
		mVC.setCurrentTeacher(currentTeacher);
		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().add(this.getClass().getResource("gui.css").toExternalForm());
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}

	public static void showManageView() throws IOException {

		Main.loadCursor();
		
		//TODO esto deberia estar en el menu principal
		Main.previousNodeQueue.clear();

		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ManageView.fxml"));
		TabPane manageView = loader.load();

		loader = new FXMLLoader(Main.class.getResource("view/student/StudentManageView.fxml"));
		BorderPane studentManageView = loader.load();
		StudentManageViewController sMVC = loader.getController();
		sMVC.setAllStudents(studentService.getAll());
		((Tab) manageView.getTabs().get(0)).setContent(studentManageView);

		loader = new FXMLLoader(Main.class.getResource("view/teacher/TeacherManageView.fxml"));
		BorderPane teacherManageView = loader.load();
		TeacherManageViewController tMVC = loader.getController();
		tMVC.setAllTeachers(teacherService.getAll());
		((Tab) manageView.getTabs().get(1)).setContent(teacherManageView);

		loader = new FXMLLoader(Main.class.getResource("view/classroom/ClassroomManageView.fxml"));
		BorderPane classroomManageView = loader.load();
		ClassroomManageViewController cMVC = loader.getController();
		cMVC.setAllClassrooms(classroomService.getAll());
		((Tab) manageView.getTabs().get(2)).setContent(classroomManageView);

		Main.mainLayout.setCenter(manageView);

		Main.defaultCursor();
	}

	public static void showStudentView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/StudentView.fxml"));
		BorderPane studentView = loader.load();
		StudentViewController sVC = loader.getController();
		sVC.setClassrooms(Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(studentView);
	}

	public static void showTeacherView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/teacher/TeacherView.fxml"));
		BorderPane teacherView = loader.load();
		TeacherViewController tVC = loader.getController();
		tVC.setClassrooms(Main.classroomService.getAll());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(teacherView);
	}

	public static void showClassroomView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/classroom/ClassroomView.fxml"));
		BorderPane classroomView = loader.load();

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(classroomView);

	}
	
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
	
	public static void showStudentSelectionView() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/StudentSelectionView.fxml"));
		BorderPane studentSelectionView = loader.load();
		StudentSelectionViewController ssVC = loader.getController();
		//TODO adaptar al caso del ADMIN
		ssVC.setClassrooms(Main.currentTeacher.getAulas());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(studentSelectionView);
	}
	
	public static void showGraphSelectionView(List<Alumno> selectedStudents) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/GraphSelectionView.fxml"));
		BorderPane graphSelectionView = loader.load();
		GraphSelectionViewController gsVC = loader.getController();
		gsVC.setData(selectedStudents, Main.utilService.getAllFunctionalAreas());

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(graphSelectionView);
	}
	
	public static void showGraphView(List<Alumno> selectedStudents, List<Areafuncional> selectedFa, List<Categorizacion> selectedCa, List<Item> selectedIt) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/graphs/GraphView.fxml"));
		BorderPane graphView = loader.load();
		GraphViewController gVC = loader.getController();
		
		if(selectedCa.size() == 0 && selectedIt.size()==0) {
			gVC.faChart(selectedStudents, selectedFa);
		}else if(selectedIt.size() == 0) {
			gVC.caChart(selectedStudents, selectedCa);
		}else {
			gVC.itChart(selectedStudents, selectedIt);
		}
		

		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(graphView);
	}

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

	public static void showEditClassroomView(Aula cla) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/classroom/EditClassroomView.fxml"));
		BorderPane editClassroomView = loader.load();
		EditClassroomViewController editClassroomController = loader.getController();
		editClassroomController.setClassroom(cla);
		
		Main.previousNodeQueue.add(Main.mainLayout.getCenter());
		Main.mainLayout.setCenter(editClassroomView);

	}

	public static void deleteStudent(Alumno stu) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar el alumno/a: " + stu.getNombre() + " " + stu.getApellido1() + " "
						+ stu.getApellido2() + "?\n" + "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			Main.studentService.delete(stu.getId());
		}
	}

	public static void deleteTeacher(Profesor tea) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar el profesor/a: " + tea.getNombre() + " " + tea.getApellido1() + " "
						+ tea.getApellido2() + "?\n" + "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			Main.teacherService.delete(tea.getId());
		}

	}

	public static void deleteClassroom(Aula cla) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que quieres borrar el aula: "
				+ cla.getNombre() + " ?\n" + "Los cambios serán definitivos.", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			Main.classroomService.delete(cla.getId());
		}

	}

	public static void goBack() {
		Main.mainLayout.setCenter(Main.previousNodeQueue.pollLast());
	}
	public static void handCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.HAND);
	}

	public static void defaultCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}

	public static void loadCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.WAIT);
	}

	public static void setModifiedData(boolean dataStatus) {
		Main.modifiedData = dataStatus;
	}

	public static boolean getDataIntegrity() {
		return Main.modifiedData;
	}
	
	public static ManageService<Alumno> getStudentService() {
		return studentService;
	}

	public static ManageService<Aula> getClassroomService() {
		return classroomService;
	}

	public static ManageService<Profesor> getTeacherService() {
		return teacherService;
	}

	public static UtilService getUtilService() {
		return utilService;
	}


	public static void main(String[] args) {
		launch(args);
	}
}
