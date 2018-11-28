package gui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import gui.view.student.EditStudentViewController;
import gui.view.student.StudentManageViewController;
import gui.view.teacher.EditTeacherViewController;
import gui.view.teacher.TeacherManageViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Alumno;
import model.Aula;
import model.Profesor;
import service.ClassroomServiceImpl;
import service.Service;
import service.StudentServiceImpl;
import service.TeacherServiceImpl;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static Service<Alumno> studentService = new StudentServiceImpl();
	private static Service<Aula> classroomService = new ClassroomServiceImpl();
	private static Service<Profesor> teacherService = new TeacherServiceImpl();
	private static boolean modifiedData = false;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Menu principal");

		showMain();
		showManageView();
	}

	private void showMain() throws IOException {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("view/MainView.fxml"));
		Main.mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		scene.getStylesheets().add(this.getClass().getResource("gui.css").toExternalForm());
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}

	public static void showManageView() throws IOException {

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

		Main.mainLayout.setCenter(manageView);
	}

	public static void showStudentView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/StudentView.fxml"));
		BorderPane studentView = loader.load();
		loader = new FXMLLoader();
		
		Main.mainLayout.setCenter(studentView);
	}

	public static void showTeacherView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/teacher/TeacherView.fxml"));
		BorderPane teacherView = loader.load();
		loader = new FXMLLoader();
		
		Main.mainLayout.setCenter(teacherView);
	}

	public static void showEditStudentView(Alumno stu) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/EditStudentView.fxml"));
		BorderPane editSudentView = loader.load();
		EditStudentViewController editStudentController = loader.getController();
		editStudentController.setStudent(stu);
		Main.mainLayout.setCenter(editSudentView);
		// Main.studentService.edit(stu.getId());

	}

	public static void showEditTeacherView(Profesor tea) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/teacher/EditTeacherView.fxml"));
		BorderPane editTeacherView = loader.load();
		EditTeacherViewController editTeacherController = loader.getController();
		editTeacherController.setTeacher(tea);
		Main.mainLayout.setCenter(editTeacherView);
		// Main.teacherService.edit(tea.getId());

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

	public static void main(String[] args) {
		launch(args);
	}
}
