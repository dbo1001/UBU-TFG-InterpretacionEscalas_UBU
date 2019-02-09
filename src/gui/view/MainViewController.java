package gui.view;

import java.io.IOException;

import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
	@FXML
	private Button exportar;
	@FXML
	private Button importar;
	@FXML
	private Label disconnect;
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
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Quieres empezar a generar un nuevo gráfico?", ButtonType.YES,
				ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
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

	@FXML
	private void exportData() throws IOException {
		if (!Main.getDataIntegrity()) {
			Main.exportData();
			Main.showManageView();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			Main.exportData();
			Main.showManageView();
		}
		
	}

	@FXML
	private void importData() throws IOException {
		if (!Main.getDataIntegrity()) {
			Main.importData();
			Main.showManageView();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			Main.importData();
			Main.showManageView();
		}
	}

	@FXML
	private void reLog() throws IOException {
		if (!Main.getDataIntegrity()) {
			reserCurrentTeacher();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			reserCurrentTeacher();
		}
	}

	private void reserCurrentTeacher() throws IOException {
		this.mainMenu.setDisable(true);
		this.generateGraphic.setDisable(true);
		this.exportar.setDisable(true);
		this.importar.setDisable(true);
		this.disconnect.setDisable(true);
		this.disconnect.setVisible(false);
		this.currentTeacher = null;
		this.teacherName.setText("");
		Main.showLogInView();
	}

	public void setCurrentTeacher(Profesor tea) {
		this.currentTeacher = tea;
		this.mainMenu.setDisable(false);
		this.generateGraphic.setDisable(false);
		this.exportar.setDisable(false);
		this.importar.setDisable(false);
		this.disconnect.setDisable(false);
		this.disconnect.setVisible(true);
		this.teacherName.setText(this.currentTeacher.getApellido1() + " " + this.currentTeacher.getApellido2() + ", "
				+ this.currentTeacher.getNombre());

	}

}
