package connection.service;

import javax.persistence.EntityManager;

import connection.EntityManagerSingleton;

public abstract class ServiceImpl<T> implements Service<T> {

	private EntityManager em;
	
	public ServiceImpl() {
		this.em = EntityManagerSingleton.getEntityManager();
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
}
