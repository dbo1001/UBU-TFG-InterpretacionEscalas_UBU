package connection.manageService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Aula;
import model.Evaluacion;
import model.Profesor;

public class ClassroomServiceImpl extends ServiceImpl implements ManageService<Aula, String> {

	@Override
	public List<Aula> getAll() {
		EntityManager em = super.getEntityManager();
		List<Aula> result = em.createNamedQuery("Aula.findAll", Aula.class).getResultList();
		if (em.isOpen()) {
			em.close();
		}
		return result;
	}

	@Override
	public Aula getOneById(long id) {
		EntityManager em = super.getEntityManager();
		Aula result;
		try {
			result = em.createNamedQuery("Aula.findById", Aula.class).setParameter("id", id).getSingleResult();
		} catch (NoResultException nrE) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public Aula getOne(String name) {
		EntityManager em = super.getEntityManager();
		Aula result;
		try {
			result = em.createNamedQuery("Aula.findByName", Aula.class).setParameter("name", name).getSingleResult();
		} catch (NoResultException nrE) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		return result;
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
				for (Profesor tea : cla.getProfesors()) {
					tea.getAulas().add(cla);
					em.merge(tea);
				}
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
				Aula oldClassroom = em.createNamedQuery("Aula.findById", Aula.class).setParameter("id", cla.getId())
						.getSingleResult();
				for (Profesor tea : oldClassroom.getProfesors()) {
					if (!cla.getProfesors().contains(tea) && tea.getAulas().contains(oldClassroom)) {
						tea.getAulas().remove(oldClassroom);
						em.merge(tea);
					}
				}
				for (Profesor tea : cla.getProfesors()) {
					if (!oldClassroom.getProfesors().contains(tea) && !tea.getAulas().contains(oldClassroom)) {
						tea.getAulas().add(cla);
						em.merge(tea);
					}
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
	public boolean delete(Aula cla) throws ConnectionException {

		EntityManager em = this.getEntityManager();

		try {
			em.getTransaction().begin();
			if (cla.getAlumnos().size() > 0) {
				throw new ConnectionException(ConnectionError.CANT_DELETE_CLASSROOM);
			}
			for (Profesor tea : cla.getProfesors()) {
				tea.getAulas().remove(cla);
				em.merge(tea);
			}
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
