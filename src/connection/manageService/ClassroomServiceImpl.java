package connection.manageService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Alumno;
import model.Aula;
import model.Categorizacion;

public class ClassroomServiceImpl extends ServiceImpl implements ManageService<Aula> {

	private static Aula aulaPruebas = new Aula();

	public void setAulaPruebas() {
		ClassroomServiceImpl.aulaPruebas.setId(6);
		ClassroomServiceImpl.aulaPruebas.setNombre("Aula6");
		ClassroomServiceImpl.aulaPruebas.setCapacidad(6);
	}

	public Aula getAulaPruebas() {
		return ClassroomServiceImpl.aulaPruebas;
	}

	@Override
	public List<Aula> getAll() {

		return getEntityManager().createNamedQuery("Aula.findAll", Aula.class).getResultList();
		/*
		 * //Codigo de ejemplo usado para testear
		 * 
		 * List<Aula> resultado = new ArrayList<Aula>(); Aula aula1 = new Aula(); Aula
		 * aula2 = new Aula(); Aula aula3 = new Aula(); Aula aula4 = new Aula(); Aula
		 * aula5 = new Aula();
		 * 
		 * aula1.setId(1); aula1.setNombre("Aula1"); aula1.setCapacidad(1);
		 * 
		 * aula2.setId(2); aula2.setNombre("Aula2"); aula2.setCapacidad(2);
		 * 
		 * aula3.setId(3); aula3.setNombre("Aula3"); aula3.setCapacidad(3);
		 * 
		 * aula4.setId(4); aula4.setNombre("Aula4"); aula4.setCapacidad(4);
		 * 
		 * aula5.setId(5); aula5.setNombre("Aula5"); aula5.setCapacidad(5);
		 * 
		 * 
		 * 
		 * resultado.add(aula1); resultado.add(aula2); resultado.add(aula3);
		 * resultado.add(aula4); resultado.add(aula5);
		 * 
		 * return resultado;
		 */
	}

	@Override
	public boolean add(Aula cla) throws ConnectionException {
		if (this.checkFields(cla)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				if (em.createNamedQuery("Aula.findByName", Aula.class).setParameter("name", cla.getNombre())
						.getResultList().size() != 0) {
					throw new ConnectionException(ConnectionError.CLASSROOM_ALREADY_EXISTS);
				}
				em.persist(cla);
				em.getTransaction().commit();
			} catch (Exception ex) {

				em.getTransaction().rollback();
				throw ex;
			} finally {
				if (em.isOpen()) {
					em.close();
				}
			}
		}

		return true;
	}

	@Override
	public boolean edit(Aula cla) throws ConnectionException {
		if (this.checkFields(cla)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				List<Aula> check = em.createNamedQuery("Aula.findByName", Aula.class)
						.setParameter("name", cla.getNombre()).getResultList();
				if (check.size() != 0 && check.get(0).getId() != cla.getId()) {
					throw new ConnectionException(ConnectionError.CLASSROOM_ALREADY_EXISTS);
				}
				em.merge(cla);
				em.getTransaction().commit();
			} catch (Exception ex) {

				em.getTransaction().rollback();
				throw ex;
			} finally {
				if (em.isOpen()) {
					em.close();
				}
			}
		}

		return true;
	}

	@Override
	public boolean delete(Aula cla) {

		EntityManager em = this.getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.remove(em.contains(cla) ? cla : em.merge(cla));
			em.getTransaction().commit();
		} catch (Exception ex) {

			em.getTransaction().rollback();
			throw ex;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

		return true;
	}

	private boolean checkFields(Aula cla) throws ConnectionException {

		if (cla.getNombre().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (cla.getNombre().length() > 50) {
			throw new ConnectionException(ConnectionError.NAME_TOO_LONG);
		} else if (super.specialCharacterPattern.matcher(cla.getNombre()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_CLASSROOM_NAME);
		}

		if (cla.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}

		return true;
	}

}
