package gui.view.evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import connection.ConnectionException;
import gui.Controller;
import gui.Main;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Alumno;
import model.Areafuncional;
import model.Categorizacion;
import model.Evaluacion;
import model.Item;
import model.Puntuacion;

public class EditEvaluationViewController extends Controller {
	@FXML
	private TabPane tabPane;

	private List<Areafuncional> allFunctionalAreas;
	private List<Categorizacion> allCategories;
	private List<Item> allItems;
	private List<ToggleGroup> rbGroups = new ArrayList<ToggleGroup>();
	private Alumno stu;
	private Evaluacion eva;
	private Map<Item, Integer> scores = new TreeMap<Item, Integer>();

	private void loadData() {

		for (Puntuacion pun : this.eva.getPuntuacions()) {
			scores.put(pun.getItem(), pun.getValoracion());
		}

		for (Areafuncional aF : this.allFunctionalAreas) {
			int i = 0;
			Tab tab = new Tab(aF.getDescripcion());
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setPadding(new Insets(5, 20, 0, 20));
			GridPane gridPane = new GridPane();
			Text studentName = new Text("*Editando evaluación del día " + this.eva.getFecha().toString().substring(0, 19)+" perteneciente al alumno/a: " + this.stu.getNombre() + " "
					+ this.stu.getApellido1() + " " + this.stu.getApellido2());
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
			// scrollPaneContent.setSpacing(15);
			// scrollPaneContent.setAlignment(Pos.CENTER);
			// gridPane.setAlignment(Pos.CENTER);
			VBox.setMargin(advise, new Insets(0, 0, 15, 0));
			;
			scrollPane.setContent(scrollPaneContent);
			scrollPane.setFitToWidth(true);
			// scrollPane.setPrefSize(ScrollPane.USE_COMPUTED_SIZE,
			// ScrollPane.USE_COMPUTED_SIZE);
			tab.setContent(scrollPane);
			for (Categorizacion cat : this.allCategories) {
				if (aF.getId() == cat.getAreafuncional().getId()) {
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
						if (item.getCategorizacion().getId() == cat.getId()) {
							Text itemText = new Text(item.getNumero() + ". " + item.getDescripcion());
							itemText.getStyleClass().add("smallText");
							textVBox.getChildren().add(itemText);
							VBox.setMargin(itemText, new Insets(0, 0, 0, 20));
							HBox rbHBox = new HBox();
							rbHBox.setSpacing(20);
							ToggleGroup toggle = new ToggleGroup();
							toggle.setUserData(item);
							for (int c = 0; c < 5; c++) {
								RadioButton rB = new RadioButton();
								rB.setUserData(c + 1);
								rB.setToggleGroup(toggle);
								rbHBox.getChildren().add(rB);
							}
							if (this.scores.containsKey(item)) {
								toggle.getToggles().get(this.scores.get(item) - 1).setSelected(true);
								toggle.selectToggle(toggle.getToggles().get(this.scores.get(item) - 1));
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

	public void setData(Evaluacion eva, List<Areafuncional> allFunctionalAreas, List<Categorizacion> allCategories,
			List<Item> allItems) {
		this.stu = eva.getAlumno();
		this.eva = eva;
		this.allFunctionalAreas = allFunctionalAreas;
		this.allCategories = allCategories;
		this.allItems = allItems;
		this.loadData();
	}

	@FXML
	private void cancel() throws IOException {
		if (this.cancelAlert()) {
			super.goBack();
		}
	}

	@FXML
	private void acept() throws IOException {
		// Calendar cal = Calendar.getInstance();
		// Date date = cal.getTime();
		// Timestamp ts = new Timestamp(date.getTime());
		// eva.setAlumno(stu);
		// eva.setFecha(ts);
		for (ToggleGroup toggle : this.rbGroups) {
			if (toggle.getSelectedToggle() != null) {
				Puntuacion pun = new Puntuacion();
				pun.setEvaluacion(eva);
				pun.setItem((Item) toggle.getUserData());
				pun.setValoracion((int) toggle.getSelectedToggle().getUserData());
				if (eva.getPuntuacions().contains(pun)) {
					eva.getPuntuacions().get(eva.getPuntuacions().indexOf(pun)).setValoracion(pun.getValoracion());
				} else {
					eva.getPuntuacions().add(pun);
				}
			}
		}
		try {
			if (Main.getEvaluationService().edit(eva)) {
				Alert alert = new Alert(AlertType.INFORMATION, "La evaluaci�n se ha actualizado correctamente",
						ButtonType.OK);
				alert.showAndWait();
				Main.setModifiedData(false);
				Main.showManageView();
			}
		} catch (ConnectionException cEx) {
			Alert alert = new Alert(AlertType.ERROR, cEx.getError().getText(), ButtonType.OK);
			alert.showAndWait();
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
