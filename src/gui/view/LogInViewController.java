package gui.view;

import java.io.IOException;

import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Profesor;

/**
 * Controlador de la pantalla de log in
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class LogInViewController extends Controller {
	
	@FXML
	private TextField Nif;
	@FXML
	private PasswordField password;
	@FXML
	private Text error;
	
	/**
	 * Comprueba los campos y logea al profesor en caso de acierto
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void logIn() throws IOException {
		Profesor tea = Main.getTeacherService().getOne(this.Nif.getText());
		if(tea == null) {
			this.error.setText("Error, no se ha encontrado ningun usuario con el NIF introducido.");
			this.error.setVisible(true);
		}else if (!tea.getContrasena().equals(Controller.encryptPassword(this.password.getText()))){
			this.error.setText("Error, la contraseña es incorrecta.");
			this.error.setVisible(true);
		} else {
			Main.setCurrentTeacher(tea);
		}
		
	}

}
