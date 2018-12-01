package connection.utilService;

import java.util.List;

import connection.ServiceImpl;
import model.Areafuncional;
import model.Categorizacion;
import model.Item;

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
	
	public boolean isOpen() {
		return this.getEntityManager().isOpen();
	}

}
