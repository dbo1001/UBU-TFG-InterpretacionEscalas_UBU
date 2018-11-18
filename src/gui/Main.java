package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private static BorderPane mainLayout;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Menu principal");

		showMain();
	}

	private void showMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Main.mainLayout = FXMLLoader.load(Main.class.getResource("view/MainView.fxml"));
		Main.mainLayout.setCenter(FXMLLoader.load(Main.class.getResource("view/ManageView.fxml")));
		Scene scene = new Scene(mainLayout);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
