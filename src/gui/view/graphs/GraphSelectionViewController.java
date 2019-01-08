package gui.view.graphs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import model.Alumno;
import model.Areafuncional;
import model.Categorizacion;
import model.Evaluacion;
import model.Item;

public class GraphSelectionViewController extends Controller {

	@FXML
	private ListView<CheckBox> functionalareaLV;
	@FXML
	private ListView<CheckBox> categoryLV;
	@FXML
	private ListView<CheckBox> itemLV;
	@FXML
	CheckBox allFaSelected;
	@FXML
	CheckBox allCaSelected;
	@FXML
	CheckBox allItSelected;
	private int checkedFa, checkedCa, checkedIt = 0;
	private List<Evaluacion> selectedEvaluations;
	private Set<Categorizacion> selectedCa = new LinkedHashSet<Categorizacion>();
	private Set<Item> selectedIt = new LinkedHashSet<Item>();
	private final EventHandler<MouseEvent> handCursor = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			handCursor();
		}

	};
	private final EventHandler<MouseEvent> defaultCursor = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			defaultCursor();
		}

	};

	@FXML
	private void initialize() {
		this.functionalareaLV.setSelectionModel(new NoSelectionModel());
		this.categoryLV.setSelectionModel(new NoSelectionModel());
		this.itemLV.setSelectionModel(new NoSelectionModel());
	}

	@FXML
	private void selectAllFa() {
		if (!this.allFaSelected.isSelected()) {
			for (CheckBox cb : this.functionalareaLV.getItems()) {
				cb.setSelected(false);
				this.removeCategories((Areafuncional) cb.getUserData());
			}
			this.checkedFa = 0;
		} else {
			for (CheckBox cb : this.functionalareaLV.getItems()) {
				cb.setSelected(true);
				this.addCategories((Areafuncional) cb.getUserData());
			}
			this.checkedFa = this.functionalareaLV.getItems().size();
		}
	}

	@FXML
	private void selectAllCa() {
		if (this.categoryLV.getItems().size() > 0) {
			if (!this.allCaSelected.isSelected()) {
				for (CheckBox cb : this.categoryLV.getItems()) {
					cb.setSelected(false);
					this.removeItems((Categorizacion) cb.getUserData());
				}
				this.checkedCa = 0;
			} else {
				for (CheckBox cb : this.categoryLV.getItems()) {
					cb.setSelected(true);
					this.addItems((Categorizacion) cb.getUserData());
				}
				this.checkedCa = this.categoryLV.getItems().size();
			}
		} else {
			this.allCaSelected.setSelected(false);
		}
	}

	@FXML
	private void selectAllIt() {
		if (this.itemLV.getItems().size() > 0) {
			if (!this.allItSelected.isSelected()) {
				for (CheckBox cb : this.itemLV.getItems()) {
					cb.setSelected(false);
				}
				this.checkedIt = 0;
			} else {
				for (CheckBox cb : this.itemLV.getItems()) {
					cb.setSelected(true);
				}
				this.checkedIt = this.itemLV.getItems().size();
			}
		} else {
			this.allItSelected.setSelected(false);
		}
	}

	@FXML
	private void generateGraph() throws IOException {
		List<Areafuncional> finalFaList = new ArrayList<Areafuncional>();
		List<Categorizacion> finalCaList = new ArrayList<Categorizacion>();
		List<Item> finalItList = new ArrayList<Item>();

		for (CheckBox cb : this.functionalareaLV.getItems()) {
			if (cb.isSelected()) {
				finalFaList.add((Areafuncional) cb.getUserData());
			}
		}

		for (CheckBox cb : this.categoryLV.getItems()) {
			if (cb.isSelected()) {
				finalCaList.add((Categorizacion) cb.getUserData());
			}
		}

		for (CheckBox cb : this.itemLV.getItems()) {
			if (cb.isSelected()) {
				finalItList.add((Item) cb.getUserData());
			}
		}

		Main.showGraphView(this.selectedEvaluations, finalFaList, finalCaList, finalItList);
	}

	public void setData(List<Evaluacion> selectedEvaluations, List<Areafuncional> faList) {
		this.selectedEvaluations = selectedEvaluations;
		for (Areafuncional fa : faList) {
			CheckBox cb = new CheckBox(fa.getDescripcion());
			cb.setUserData(fa);
			cb.setPrefHeight(20);
			cb.setOnMouseEntered(handCursor);
			cb.setOnMouseExited(defaultCursor);
			cb.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if (!cb.isSelected()) {
						allFaSelected.setSelected(false);
						removeCategories((Areafuncional) cb.getUserData());
						checkedFa--;
					} else {
						addCategories((Areafuncional) cb.getUserData());
						checkedFa++;
						if (checkedFa == functionalareaLV.getItems().size()) {
							allFaSelected.setSelected(true);
						}
					}

				}

			});
			this.functionalareaLV.getItems().add(cb);
		}
	}

	private void addCategories(Areafuncional fa) {
		this.selectedCa.addAll(fa.getCategorizacions());
		this.allCaSelected.setSelected(false);
		this.updateCategories();
	}

	private void removeCategories(Areafuncional fa) {
		this.selectedCa.removeAll(fa.getCategorizacions());
		this.updateCategories();
	}

	private void updateCategories() {
		this.categoryLV.getItems().clear();
		this.selectedIt.clear();
		this.allCaSelected.setSelected(false);
		this.allItSelected.setSelected(false);
		this.checkedCa = 0;
		this.checkedIt = 0;
		this.itemLV.getItems().clear();
		for (Categorizacion ca : this.selectedCa) {
			CheckBox categoryCB = new CheckBox(ca.getDescripcion());
			categoryCB.setUserData(ca);
			categoryCB.setPrefHeight(20);
			categoryCB.setOnMouseEntered(handCursor);
			categoryCB.setOnMouseExited(defaultCursor);
			categoryCB.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if (!categoryCB.isSelected()) {
						allCaSelected.setSelected(false);
						removeItems((Categorizacion) categoryCB.getUserData());
						checkedCa--;
					} else {
						addItems((Categorizacion) categoryCB.getUserData());
						checkedCa++;
						if (checkedCa == categoryLV.getItems().size()) {
							allCaSelected.setSelected(true);
						}
					}

				}

			});
			this.categoryLV.getItems().add(categoryCB);
		}
	}

	private void addItems(Categorizacion ca) {
		this.selectedIt.addAll(ca.getItems());
		this.allItSelected.setSelected(false);
		this.updateItems();
	}

	private void removeItems(Categorizacion ca) {
		this.selectedIt.removeAll(ca.getItems());
		this.updateItems();
	}

	private void updateItems() {
		this.itemLV.getItems().clear();
		this.allItSelected.setSelected(false);
		this.checkedIt = 0;
		for (Item it : this.selectedIt) {
			CheckBox itemCB = new CheckBox(it.getDescripcion());
			itemCB.setUserData(it);
			itemCB.setPrefHeight(20);
			itemCB.setOnMouseEntered(handCursor);
			itemCB.setOnMouseExited(defaultCursor);
			itemCB.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if (itemCB.isSelected()) {
						checkedIt++;
						if (checkedIt == itemLV.getItems().size()) {
							allItSelected.setSelected(true);
						}
					} else {
						allItSelected.setSelected(false);
						checkedIt--;
					}

				}

			});
			this.itemLV.getItems().add(itemCB);
		}
	}

	protected class NoSelectionModel extends MultipleSelectionModel<CheckBox> {

		@Override
		public ObservableList<Integer> getSelectedIndices() {
			return FXCollections.emptyObservableList();
		}

		@Override
		public ObservableList<CheckBox> getSelectedItems() {
			return FXCollections.emptyObservableList();
		}

		@Override
		public void selectIndices(int index, int... indices) {
		}

		@Override
		public void selectAll() {
		}

		@Override
		public void selectFirst() {
		}

		@Override
		public void selectLast() {
		}

		@Override
		public void clearAndSelect(int index) {
		}

		@Override
		public void select(int index) {
		}

		@Override
		public void select(CheckBox obj) {
		}

		@Override
		public void clearSelection(int index) {
		}

		@Override
		public void clearSelection() {
		}

		@Override
		public boolean isSelected(int index) {
			return false;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public void selectPrevious() {
		}

		@Override
		public void selectNext() {
		}
	}
}
