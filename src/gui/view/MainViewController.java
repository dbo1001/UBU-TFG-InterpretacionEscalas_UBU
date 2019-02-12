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

/**
 * Controlador de la barra de botones que siempre está presente
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
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

	/**
	 * Comprueba la integridad de los datos
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void checkDataIntegrity() throws IOException {
		if (!Main.getDataIntegrity()) {
			Main.showManageView();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			Main.showManageView();
		}
	}

	/**
	 * Muestra la pantalla de selección de alumnos, comienza a generar un gráfico
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void goToStudentSelectionView() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "¿Quieres empezar a generar un nuevo gráfico?", ButtonType.YES,
				ButtonType.NO);
		alert.initOwner(Main.getPrimaryStage());
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			try {
				Main.showStudentSelectionView();
			} catch (IOException e1) {
				alert = new Alert(AlertType.ERROR,
						"Ha ocurrido un error desconocido, porfavor reinicie la aplicación. (Es posible que no se haya encontrado uno de los archivos necesarios para la ejecución)");
				alert.initOwner(Main.getPrimaryStage());
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Exporta los datos a la nube
	 * @throws IOException archivo no encontrado
	 */
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

	/**
	 * Comienza el proceso de importación de los datos de la nube
	 * @throws IOException error al descargar los archivos, no se han podido crear o sobreescribir
	 */
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

	/**
	 * Desconecta al profesor logeado actualmente y vuelve a la pantalla de log in
	 * @throws IOException archivo no encontrado
	 */
	@FXML
	private void reLog() throws IOException {
		if (!Main.getDataIntegrity()) {
			resetCurrentTeacher();
		} else if (this.cancelAlert()) {
			Main.setModifiedData(false);
			resetCurrentTeacher();
		}
	}

	/**
	 * Desconecta al profesor logeado actualmente y resetea la interfaz
	 * @throws IOException archivo no encontrado
	 */
	private void resetCurrentTeacher() throws IOException {
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

	/**
	 * Logea un profesor
	 * @param tea profesor que ha hecho log in
	 */
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
