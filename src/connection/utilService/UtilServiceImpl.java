package connection.utilService;

import java.util.List;

import connection.ServiceImpl;
import model.Areafuncional;
import model.Categorizacion;
import model.Item;

/**
 * Implementación de la interfaz UtilService
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class UtilServiceImpl extends ServiceImpl implements UtilService {

	@Override
	public List<Areafuncional> getAllFunctionalAreas() {
		return getEntityManager().createNamedQuery("Areafuncional.findAll", Areafuncional.class).getResultList();
	}

	@Override
	public List<Categorizacion> getAllCategories() {
		return getEntityManager().createNamedQuery("Categorizacion.findAll", Categorizacion.class).getResultList();
	}

	@Override
	public List<Item> getAllItems() {
		return getEntityManager().createNamedQuery("Item.findAll", Item.class).getResultList();
	}
	
	/**
	 * Consulta si el entityManager esta activo
	 * @return estado del entityManager
	 */
	public boolean isOpen() {
		return this.getEntityManager().isOpen();
	}

}
