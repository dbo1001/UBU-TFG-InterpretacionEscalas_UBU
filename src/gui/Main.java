package gui;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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

public class Main extends Application {

	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static Service service = new ServiceImpl();

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Menu principal");

		showMain();
		showManage();
	}

	private void showMain() throws IOException {
		Main.mainLayout = FXMLLoader.load(Main.class.getResource("view/MainView.fxml"));
		Scene scene = new Scene(mainLayout);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private void showManage() throws IOException {
		TabPane manage = FXMLLoader.load(Main.class.getResource("view/ManageView.fxml"));
		GridPane studentsGrid = ((GridPane) ((AnchorPane) manage.getTabs().get(0).getContent()).getChildren().get(0));

		loadStudents(studentsGrid, Main.service.getStudents());

		Main.mainLayout.setCenter(manage);
		// Scene scene = new Scene(mainLayout);
		// Scene scene = new Scene(mainLayout);
		// this.primaryStage.setScene(scene);
		// this.primaryStage.show();
	}

	private void loadStudents(GridPane grid, List<Alumno> students) {
		Text text;
		Label edit = new Label("e");
		Label delete;
		HBox hbox;
		int i = 1;
		EventHandler<MouseEvent> mouseOver = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.primaryStage.getScene().setCursor(Cursor.HAND);
			}
		};
		EventHandler<MouseEvent> mouseLeft = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Main.primaryStage.getScene().setCursor(Cursor.DEFAULT);
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
					Main.service.editStudent(stu.getId());
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
					Main.service.deleteStudent(stu.getId());
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

	public static void main(String[] args) {
		launch(args);
	}
}
