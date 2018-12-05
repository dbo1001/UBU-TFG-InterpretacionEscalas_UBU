package gui.view.evaluation;

import java.util.ArrayList;
import java.util.List;

import gui.view.Controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Areafuncional;
import model.Categorizacion;
import model.Item;

public class EvaluationViewController extends Controller {
	
	@FXML
	TabPane tabPane;
	
	List<Areafuncional> allFunctionalAreas;
	List<Categorizacion> allCategories;
	List<Item> allItems;

	private void loadData() {
		
		for (Areafuncional aF : this.allFunctionalAreas) {
			int i = 0;
			Tab tab = new Tab(aF.getDescripcion());
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setPadding(new Insets(15, 20, 50, 20));
			GridPane gridPane = new GridPane();
			scrollPane.setContent(gridPane);
			scrollPane.setPrefSize(ScrollPane.USE_COMPUTED_SIZE, ScrollPane.USE_COMPUTED_SIZE);
			tab.setContent(scrollPane);
			for(Categorizacion cat : this.allCategories) {
				if(aF.equals(cat.getAreafuncional())) {
					VBox textVBox = new VBox();
					textVBox.setSpacing(10);
					VBox buttonsVBox = new VBox();
					buttonsVBox.setSpacing(10);
					gridPane.add(textVBox, 0, i);
					gridPane.add(buttonsVBox, 1, i);
					gridPane.setVgap(30);
					gridPane.setHgap(20);
					
					Text catTitle = new Text(cat.getDescripcion());
					catTitle.getStyleClass().add("bigText");
					textVBox.getChildren().add(catTitle);
					HBox scoresHBox = new HBox();
					scoresHBox.setSpacing(33);
					scoresHBox.setPadding(new Insets(0,0,0,4));
					for (int c = 1; c < 6; c++) {
						Text score = new Text("" + c);
						score.getStyleClass().add("mediumText");
						scoresHBox.getChildren().add(score);
					}
					
					buttonsVBox.getChildren().add(scoresHBox);
					for(Item item : this.allItems) {
						if(item.getCategorizacion().equals(cat)) {
							Text itemText = new Text(item.getNumero() + ". " + item.getDescripcion());
							itemText.getStyleClass().add("smallText");
							textVBox.getChildren().add(itemText);
							VBox.setMargin(itemText, new Insets(0, 0, 0, 20));
							HBox rbHBox = new HBox();
							rbHBox.setSpacing(20);
							ToggleGroup toggle = new ToggleGroup();
							toggle.setUserData(item.getNumero());
							for(int c = 0; c < 5; c++) {
								RadioButton rB = new RadioButton();
								rB.setToggleGroup(toggle);
								rbHBox.getChildren().add(rB);
							}
							buttonsVBox.getChildren().add(rbHBox);
						}
					}
					i++;
				}
			}
			tabPane.getTabs().add(tab);
		}
	}
	
	public void setData (List<Areafuncional> allFunctionalAreas, List<Categorizacion> allCategories, List<Item> allItems) {
		this.allFunctionalAreas = allFunctionalAreas;
		this.allCategories = allCategories;
		this.allItems = allItems;
		this.loadData();
	}
	
}
