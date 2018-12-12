package gui.view.evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Alumno;
import model.Areafuncional;
import model.Categorizacion;
import model.Item;

public class EvaluationViewController extends Controller {

	@FXML
	TabPane tabPane;

	List<Areafuncional> allFunctionalAreas;
	List<Categorizacion> allCategories;
	List<Item> allItems;
	List<ToggleGroup> rbGroups = new ArrayList<ToggleGroup>();
	Alumno stu;

	private void loadData() {

		for (Areafuncional aF : this.allFunctionalAreas) {
			int i = 0;
			Tab tab = new Tab(aF.getDescripcion());
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setPadding(new Insets(5, 20, 0, 20));
			GridPane gridPane = new GridPane();
			Text studentName = new Text("*Creando nueva evaluación para " + this.stu.getNombre() + " " + this.stu.getApellido1() + " "
					+ this.stu.getApellido2());
			studentName.getStyleClass().add("littleText");
			Text advise = new Text(
					"**Se evaluará el grado de adquisición de la habilidad en una escala de tipo Likert de 1 a 5 donde 1 = nunca o nada 5 = todo o siempre.");
			advise.getStyleClass().add("littleText");
			/*
			 * AnchorPane anchorPane = new AnchorPane(); AnchorPane.setRightAnchor(gridPane,
			 * 0.0); AnchorPane.setLeftAnchor(gridPane, 0.0);
			 * AnchorPane.setTopAnchor(gridPane, 0.0); gridPane.setGridLinesVisible(true);
			 * anchorPane.getChildren().add(gridPane);
			 */
			VBox scrollPaneContent = new VBox(studentName, advise, gridPane);
			//scrollPaneContent.setSpacing(15);
			VBox.setMargin(advise, new Insets(0, 0, 15, 0));;
			scrollPane.setContent(scrollPaneContent);
			// scrollPane.setFitToWidth(true);
			// scrollPane.setPrefSize(ScrollPane.USE_COMPUTED_SIZE,
			// ScrollPane.USE_COMPUTED_SIZE);
			tab.setContent(scrollPane);
			for (Categorizacion cat : this.allCategories) {
				if (aF.equals(cat.getAreafuncional())) {
					VBox textVBox = new VBox();
					textVBox.setSpacing(10);
					VBox buttonsVBox = new VBox();
					// AnchorPane columnAP = new AnchorPane();
					// buttonsVBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					buttonsVBox.setSpacing(10);
					// columnAP.getChildren().add(buttonsVBox);
					gridPane.add(textVBox, 0, i);
					gridPane.add(buttonsVBox, 1, i);
					// gridPane.add(columnAP, 1, i);
					gridPane.setVgap(30);
					gridPane.setHgap(20);
					// GridPane.setHgrow(columnAP, Priority.ALWAYS);
					// AnchorPane.setRightAnchor(buttonsVBox, 0.0);

					Text catTitle = new Text(cat.getDescripcion());
					catTitle.getStyleClass().add("bigText");
					textVBox.getChildren().add(catTitle);
					HBox scoresHBox = new HBox();
					scoresHBox.setSpacing(33);
					scoresHBox.setPadding(new Insets(0, 0, 0, 4));
					for (int c = 1; c < 6; c++) {
						Text score = new Text("" + c);
						score.getStyleClass().add("mediumText");
						scoresHBox.getChildren().add(score);
					}

					buttonsVBox.getChildren().add(scoresHBox);
					for (Item item : this.allItems) {
						if (item.getCategorizacion().equals(cat)) {
							Text itemText = new Text(item.getNumero() + ". " + item.getDescripcion());
							itemText.getStyleClass().add("smallText");
							textVBox.getChildren().add(itemText);
							VBox.setMargin(itemText, new Insets(0, 0, 0, 20));
							HBox rbHBox = new HBox();
							rbHBox.setSpacing(20);
							ToggleGroup toggle = new ToggleGroup();
							toggle.setUserData(item.getNumero());
							for (int c = 0; c < 5; c++) {
								RadioButton rB = new RadioButton();
								rB.setUserData(c+1);
								rB.setToggleGroup(toggle);
								rbHBox.getChildren().add(rB);
							}
							this.rbGroups.add(toggle);
							buttonsVBox.getChildren().add(rbHBox);
						}
					}
					i++;
				}
			}
			tabPane.getTabs().add(tab);
		}
	}

	public void setData(Alumno stu, List<Areafuncional> allFunctionalAreas, List<Categorizacion> allCategories,
			List<Item> allItems) {
		this.stu = stu;
		this.allFunctionalAreas = allFunctionalAreas;
		this.allCategories = allCategories;
		this.allItems = allItems;
		this.loadData();
	}

	@FXML
	private void cancel() throws IOException {
		if (this.cancelAlert()) {
			Main.showManageView();
		}
	}
	
	@FXML
	private void acept() throws IOException {
		System.out.println("Transaccion no implementada, los rbs seleccionados son:");
		for(ToggleGroup toggle : this.rbGroups) {
			if(toggle.getSelectedToggle() != null) {
				System.out.println("El item " + toggle.getUserData() + " tiene una nota de " + toggle.getSelectedToggle().getUserData());
			}
		}
	}

	@FXML
	private void next() {
		this.tabPane.getSelectionModel().selectNext();
	}

	@FXML
	private void previous() {
		this.tabPane.getSelectionModel().selectPrevious();
	}
	
	

}
