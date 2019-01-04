package connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerGenerator {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFG_InterpretacionEscalas_UBU");
	
	public static EntityManager getNewEntityManager() {
		return emf.createEntityManager();
		
	}
}
