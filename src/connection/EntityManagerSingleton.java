package connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

	private static EntityManager em;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFG_InterpretacionEscalas_UBU");
	
	public static EntityManager getEntityManager() {
		if(em == null){
			em = emf.createEntityManager();
			return em;
		}else {
			return em;
		}
		
	}
}
