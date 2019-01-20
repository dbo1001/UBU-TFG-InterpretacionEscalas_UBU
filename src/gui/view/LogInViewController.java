package gui.view;

import java.io.IOException;

import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Profesor;

public class LogInViewController extends Controller {
	
	@FXML
	private TextField Nif;
	@FXML
	private TextField password;
	@FXML
	private Text error;
	
	@FXML
	private void logIn() throws IOException {
		Profesor tea = Main.getTeacherService().getOne(this.Nif.getText());
		if(tea == null) {
			this.error.setText("Error, no se ha encontrado ningun usuario con el NIF introducido.");
			this.error.setVisible(true);
		}else if (!tea.getContrasena().replaceAll("\\s", "").equals(this.password.getText())){
			this.error.setText("Error, la contraseña es incorrecta.");
			this.error.setVisible(true);
			System.out.println(tea.getContrasena()+"a");
		} else {
			Main.setCurrentTeacher(tea);
		}
		
	}

}
