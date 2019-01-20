package gui.view;

import java.io.IOException;

import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Profesor;

public class MainViewController extends Controller {
	
	@FXML
	private Text teacherName;
	@FXML
	private Button mainMenu;
	@FXML
	private Button generateGraphic;
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
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Quieres empezar a generar un nuevo gráfico?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES) {
			try {
				Main.showStudentSelectionView();
			} catch (IOException e1) {
				alert = new Alert(AlertType.ERROR,
						"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}
	
	public void setCurrentTeacher(Profesor tea) {
		this.currentTeacher = tea;
		this.mainMenu.setDisable(false);
		this.generateGraphic.setDisable(false);
		this.teacherName.setText(this.currentTeacher.getApellido1() + " " + this.currentTeacher.getApellido2() + ", " + this.currentTeacher.getNombre());
	}

}
