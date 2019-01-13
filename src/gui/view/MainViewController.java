package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import model.Profesor;

public class MainViewController extends Controller {
	
	@FXML
	private Text teacherName;
	private Profesor currentTeacher;

	@FXML
	private void checkDataIntegrity() throws IOException {
		if (!Main.getDataIntegrity()) {
			Main.showManageView();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
	}
	
	@FXML
	private void goToStudentSelectionView() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "�Quieres empezar a generar un nuevo gr�fico?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES) {
			try {
				Main.showStudentSelectionView();
			} catch (IOException e1) {
				alert = new Alert(AlertType.ERROR,
						"Ha ocurrido un error desconocido, porfavor reinicie la aplicaci�n. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecuci�n)");
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}
	
	public void setCurrentTeacher(Profesor tea) {
		this.currentTeacher = tea;
		this.teacherName.setText(this.currentTeacher.getApellido1() + " " + this.currentTeacher.getApellido2() + ", " + this.currentTeacher.getNombre());
	}

}
