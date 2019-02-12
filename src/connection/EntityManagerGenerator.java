package connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * 
 * @author Mario Núñez Izquierdo
 *
 */
public class EntityManagerGenerator {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFG_InterpretacionEscalas_UBU");
	
	/**
	 * Generador de entityManagers
	 * @return nuevo entityManager
	 */
	public static EntityManager getNewEntityManager() {
		return emf.createEntityManager();
		
	}
}
