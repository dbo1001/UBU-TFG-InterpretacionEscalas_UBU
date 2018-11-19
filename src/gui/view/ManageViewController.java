package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;

public class ManageViewController {
	
	@FXML
	private void addNewStudent() throws IOException {
		Main.showStudentView();
	}

}
