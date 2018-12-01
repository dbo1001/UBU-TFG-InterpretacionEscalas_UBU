package connection;

import javax.persistence.EntityManager;

import connection.manageService.ManageService;

public abstract class ServiceImpl {

	private EntityManager em;
	
	public ServiceImpl() {
		this.em = EntityManagerSingleton.getEntityManager();
	}
	
	protected EntityManager getEntityManager() {
		return this.em;
	}
}
