package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

public class Main extends Application {

	private Stage primaryStage;
	private static BorderPane mainLayout;

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
		Text text;
		Label edit = new Label("Editar");
		edit.setFont(new Font(18));
		edit.setTextFill(Color.web("3366bb"));
		edit.setUnderline(true);;
		edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					try {
						showMain();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				};
			});
		
		Label delete = new Label ("Borrar");
		delete.setFont(new Font(18));
		delete.setTextFill(Color.web("3366bb"));
		delete.setUnderline(true);;
		delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				try {
					showMain();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
			};
		});
		
		HBox hbox = new HBox(15);
		HBox.setMargin(edit, new Insets(0,0,0,30));
		hbox.setPadding(new Insets(5,0,0,0));
		
		TabPane manage = FXMLLoader.load(Main.class.getResource("view/ManageView.fxml"));
		GridPane studentsGrid = ((GridPane) ((AnchorPane) manage.getTabs().get(0).getContent()).getChildren().get(0));
		
		studentsGrid.addRow(3);
		text = new Text ("Iustakio Gomez Gutierrez");
		text.setFont(new Font(24));
		studentsGrid.add(text, 0, 3);
		//Meter las labes a la hbox y hacer los eventhandlers
		hbox.getChildren().addAll(edit, delete);
		
		
		studentsGrid.add(hbox, 1, 3);
		
		Main.mainLayout.setCenter(manage);
		//Scene scene = new Scene(mainLayout);
		//Scene scene = new Scene(mainLayout);
		//this.primaryStage.setScene(scene);
		//this.primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
