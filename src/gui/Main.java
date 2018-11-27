package gui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import gui.view.ManageViewController;
import gui.view.student.EditStudentViewController;
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
import service.ClassroomServiceImpl;
import service.Service;
import service.StudentServiceImpl;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static Service<Alumno> studentService = new StudentServiceImpl();
	private static Service<Aula> classroomService = new ClassroomServiceImpl();
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
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}

	public static void showManageView() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ManageView.fxml"));
		TabPane manageView = loader.load();
		//TableView<Alumno> studentsTable = (TableView<Alumno>) ((BorderPane) manageView.getTabs().get(0).getContent()).getCenter();
		ManageViewController mVC = loader.getController();
		
		mVC.setAllStudents(studentService.getAll());
	
		Main.mainLayout.setCenter(manageView);
		
		/*
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ManageView.fxml"));
		TabPane manageView = loader.load();
		GridPane studentsGrid = ((GridPane) ((AnchorPane) manageView.getTabs().get(0).getContent()).getChildren()
				.get(0));
	
		loadStudents(studentsGrid, Main.studentService.getAll());
	
		Main.mainLayout.setCenter(manageView);
		// Scene scene = new Scene(mainLayout);
		// Scene scene = new Scene(mainLayout);
		// this.primaryStage.setScene(scene);
		// this.primaryStage.show();
		 * 
		 */
	}

	public static void showStudentView() throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/StudentView.fxml"));
		BorderPane studentView = loader.load();
		loader = new FXMLLoader();
		// loader.setLocation(Main.class.getResource("view/student/StudentBBView.fxml"));
		// ButtonBar studentsBBView = loader.load();
		Main.mainLayout.setCenter(studentView);
		// Main.mainLayout.setBottom(studentsBBView);
	}

	public static void showEditStudentView(Alumno stu) throws IOException {
		Main.modifiedData = true;
		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource("view/student/EditStudentView.fxml"));
		BorderPane editSudentView = loader.load();
		EditStudentViewController editStudentController = loader.getController();
		editStudentController.setStudent(stu);
		Main.mainLayout.setCenter(editSudentView);
		Main.studentService.edit(stu.getId());
	
	}

	public static void deleteStudent(Alumno stu) {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"¿Estás seguro de que quieres borrar el alumno/a: " + stu.getNombre() + " "
						+ stu.getApellido1() + " " + stu.getApellido2() + "?\n"
						+ "Los cambios serán definitivos.",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
	
		if (alert.getResult() == ButtonType.YES) {
			Main.studentService.delete(stu.getId());
		}
	}

	public static void handCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.HAND);
	}

	public static void defaultCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}

	/*
	private static void loadStudents(GridPane grid, List<Alumno> students) {
		Text text;
		Label edit = new Label("e");
		Label delete;
		HBox hbox;
		int i = 1;

		EventHandler<MouseEvent> mouseOver = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.handCursor();
			}
		};
		EventHandler<MouseEvent> mouseLeft = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.defaultCursor();
			}
		};

		for (Alumno stu : students) {

			edit = new Label("Editar");
			edit.setFont(new Font(18));
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
			delete.setFont(new Font(18));
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

			hbox = new HBox(15);
			hbox.setPadding(new Insets(5, 0, 0, 0));
			HBox.setMargin(edit, new Insets(0, 0, 0, 30));
			hbox.getChildren().addAll(edit, delete);

			text = new Text(stu.getApellido1() + " " + stu.getApellido2() + ", " + stu.getNombre());
			text.setFont(new Font(24));

			grid.addRow(i);
			grid.add(text, 0, i);
			grid.add(hbox, 1, i);
			i++;
		}
	}
	*/
	
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
