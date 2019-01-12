package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;
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
	
	public void setCurrentTeacher(Profesor tea) {
		this.currentTeacher = tea;
		this.teacherName.setText(this.currentTeacher.getApellido1() + " " + this.currentTeacher.getApellido2() + ", " + this.currentTeacher.getNombre());
	}

}
