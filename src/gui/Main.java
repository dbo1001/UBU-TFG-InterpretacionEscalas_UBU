package gui;

import java.io.IOException;
import java.util.List;

import gui.view.student.EditStudentViewController;
import javafx.application.Application;
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
import service.Service;
import service.ServiceImpl;
import service.StudentService;
import service.StudentServiceImpl;

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static StudentService studentService = new StudentServiceImpl();

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

	public static void handCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.HAND);
	}

	public static void defaultCursor() {
		Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
	}

	public static void showManageView() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/ManageView.fxml"));
		TabPane manageView = loader.load();
		GridPane studentsGrid = ((GridPane) ((AnchorPane) manageView.getTabs().get(0).getContent()).getChildren()
				.get(0));

		loadStudents(studentsGrid, Main.studentService.getStudents());

		Main.mainLayout.setBottom(null);
		Main.mainLayout.setCenter(manageView);
		// Scene scene = new Scene(mainLayout);
		// Scene scene = new Scene(mainLayout);
		// this.primaryStage.setScene(scene);
		// this.primaryStage.show();
	}

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
						FXMLLoader loader = new FXMLLoader(
								this.getClass().getResource("view/student/EditStudentView.fxml"));
						BorderPane editSudentView = loader.load();
						EditStudentViewController editStudentController = loader.getController();
						editStudentController.setStudent(stu);
						Main.mainLayout.setCenter(editSudentView);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.err.println("Error, archivo EditStudentView.fxml no encontrado en la carpeta view.");
						e1.printStackTrace();
					}
					Main.studentService.editStudent(stu.getId());
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
					Alert alert = new Alert(AlertType.CONFIRMATION,
							"¿Estás seguro de que quieres borrar el alumno/a: " + stu.getNombre() + " "
									+ stu.getApellido1() + " " + stu.getApellido2() + "?\n"
									+ "Los cambios serán definitivos.",
							ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
					alert.showAndWait();
					
					if(alert.getResult() == ButtonType.YES) {
						Main.studentService.deleteStudent(stu.getId());
					}
				};
				
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

	public static void showStudentView() throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/student/StudentView.fxml"));
		BorderPane studentView = loader.load();
		loader = new FXMLLoader();
		//loader.setLocation(Main.class.getResource("view/student/StudentBBView.fxml"));
		//ButtonBar studentsBBView = loader.load();
		Main.mainLayout.setCenter(studentView);
		//Main.mainLayout.setBottom(studentsBBView);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
