package connection.utilService;

import java.util.List;

import model.Areafuncional;
import model.Categorizacion;
import model.Item;
/**
 * Interfaz para acceder a las tablas de la base de datos que no pueden ser modificadas
 * por el usuario
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public interface UtilService {

	/**
	 * Devuelve todas las áreas funcionales de la base de datos
	 * @return áreas funcionales
	 */
	public List<Areafuncional> getAllFunctionalAreas();
	
	/**
	 * Devuelve todas las categorías de la base de datos
	 * @return categorías
	 */
	public List<Categorizacion> getAllCategories();
	
	/**
	 * Devuelve todas los ítems de la base de datos
	 * @return ítems
	 */
	public List<Item> getAllItems();
	
}
