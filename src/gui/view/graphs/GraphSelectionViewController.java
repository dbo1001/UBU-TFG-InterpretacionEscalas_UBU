package gui.view.graphs;

import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.view.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import model.Alumno;
import model.Areafuncional;

public class GraphSelectionViewController extends Controller {

	@FXML
	private ListView<CheckBox> funtionalareaLV;
	@FXML 
	private ListView<CheckBox> categoryLV;
	@FXML
	CheckBox allFaSelected;
	private List<Alumno> selectedStudents;

	@FXML
	private void initialize() {
		this.funtionalareaLV.setSelectionModel(new NoSelectionModel());
	}
	
	@FXML
	private void selectAllFa() {
		if(!this.allFaSelected.isSelected()) {
			for(CheckBox cb : this.funtionalareaLV.getItems()) {
				cb.setSelected(false);
			}
		}else {
			for(CheckBox cb : this.funtionalareaLV.getItems()) {
				cb.setSelected(true);
			}
		}
	}

	public void setData(List<Alumno> selectedStudents, List<Areafuncional> faList) {
		this.selectedStudents = selectedStudents;
		for (Areafuncional fa : faList) {
			CheckBox cb = new CheckBox(fa.getDescripcion());
			cb.setUserData(fa);
			cb.setPrefHeight(20);
			cb.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if(!cb.isSelected()) {
						allFaSelected.setSelected(false);
						addCategories((Areafuncional) cb.getUserData());
					}else {
						removeCategories((Areafuncional) cb.getUserData());
					}
					
				}
				
			});
			this.funtionalareaLV.getItems().add(cb);
		}
	}
	
	private void addCategories(Areafuncional fa) {
		
	}
	
	private void removeCategories(Areafuncional fa) {
		
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
