package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;

public class ManageViewController extends Controller{
	
	@FXML
	private void addNewStudent() throws IOException {
		Main.showStudentView();
	}

}
