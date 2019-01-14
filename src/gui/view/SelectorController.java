package gui.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public abstract class SelectorController<T> extends Controller {

	@FXML
	private ListView<T> objectsDisplayed;
	@FXML
	private ListView<T> objectsSelected;
	private Comparator<T> comparator;
	
	@FXML
	private void select() {
		this.objectsSelected.getItems().addAll(this.objectsDisplayed.getSelectionModel().getSelectedItems());
		this.objectsDisplayed.getItems().removeAll(this.objectsDisplayed.getSelectionModel().getSelectedItems());
		this.sortObjects();
	}

	@FXML
	private void deselect() {
		this.objectsDisplayed.getItems().addAll(this.objectsSelected.getSelectionModel().getSelectedItems());
		this.objectsSelected.getItems().removeAll(this.objectsSelected.getSelectionModel().getSelectedItems());
		this.sortObjects();
	}
	
	protected void initialize(Callback<ListView<T>, ListCell<T>> callback, List<T> listAllObjects, Comparator<T> comparator) {
		this.objectsDisplayed.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.objectsDisplayed.setCellFactory(callback);
		this.objectsSelected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.objectsSelected.setCellFactory(callback);
		
		this.comparator = comparator;
		this.sortObjects();
		this.objectsDisplayed.getItems().addAll(listAllObjects);
	}
	
	protected void sortObjects() {
		Collections.sort(this.objectsDisplayed.getItems(), comparator);
		Collections.sort(this.objectsSelected.getItems(), comparator);
	}
	
	protected List<T> getSelectedObjects() {
		return this.objectsSelected.getItems();
	}
	
	protected List<T> getDisplayedObjects() {
		return this.objectsDisplayed.getItems();
	}	
	
}
