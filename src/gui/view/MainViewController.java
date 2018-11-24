package gui.view;

import java.io.IOException;

import gui.Main;
import javafx.fxml.FXML;

public class MainViewController extends Controller {

	@FXML
	private void checkDataIntegrity() throws IOException {
		if(!Main.getDataIntegrity()) {
			Main.showManageView();
		}else if (this.cancelAlert()){
			Main.setModifiedData(false);
			Main.showManageView();
		}
	}
	
}
