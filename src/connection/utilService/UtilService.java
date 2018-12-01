package connection.utilService;

import java.util.List;

import model.Areafuncional;
import model.Categorizacion;
import model.Item;

public interface UtilService {

	public List<Areafuncional> getAllFunctionalAreas();
	
	public List<Categorizacion> getAllCategories();
	
	public List<Item> getAllItems();
	
}
