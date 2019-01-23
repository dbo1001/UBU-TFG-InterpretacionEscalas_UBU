package connection.manageService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Alumno;
import model.Aula;
import model.Profesor;

public class TeacherServiceImpl extends ServiceImpl implements ManageService<Profesor, String> {

	@Override
	public List<Profesor> getAll() {
		EntityManager em = super.getEntityManager();
		List<Profesor> result = em.createNamedQuery("Profesor.findAll", Profesor.class).getResultList();
		if(em.isOpen()) {
			em.close();
		}
		return result;
	}

	@Override
	public Profesor getOne(String nif) {
		EntityManager em = super.getEntityManager();
		Profesor result;
		try {
			result = em.createNamedQuery("Profesor.findByNIF", Profesor.class).setParameter("nif", nif).getSingleResult();
		} catch(NoResultException nrE) {
			return null;
		}finally {
			if(em.isOpen()) {
				em.close();
			}
		}
		
		return result;
	}

	@Override
	public boolean add(Profesor tea) throws ConnectionException {
		if (this.checkFields(tea)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				if (em.createNamedQuery("Profesor.findByNIF", Profesor.class).setParameter("nif", tea.getNif())
						.getResultList().size() != 0) {
					throw new ConnectionException(ConnectionError.TEACHER_ALREADY_EXISTS);
				}
				em.persist(tea);
				for (Aula cla : tea.getAulas()) {
					cla.getProfesors().add(tea);
					em.merge(cla);
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
	public boolean edit(Profesor tea) throws ConnectionException {
		if (this.checkFields(tea)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				List<Profesor> check = em.createNamedQuery("Profesor.findByNIF", Profesor.class)
						.setParameter("nif", tea.getNif()).getResultList();
				if (check.size() != 0 && check.get(0).getId() != tea.getId()) {
					throw new ConnectionException(ConnectionError.TEACHER_ALREADY_EXISTS);
				} else if (tea.getId() == 1 && tea.getPermisos() == false) {
					throw new ConnectionException(ConnectionError.WRONG_ADMIN_RIGHTS);
				}
				Profesor oldTeacher = em.createNamedQuery("Profesor.findById", Profesor.class).setParameter("id", tea.getId())
						.getSingleResult();
				for(Aula cla: oldTeacher.getAulas()) {
					if(!tea.getAulas().contains(cla) && cla.getProfesors().contains(oldTeacher)){
						cla.getProfesors().remove(oldTeacher);
						em.merge(cla);
					}
				}
				for(Aula cla: tea.getAulas()) {
					if(!oldTeacher.getAulas().contains(cla) && !cla.getProfesors().contains(oldTeacher)) {
						cla.getProfesors().add(tea);
						em.merge(cla);
					}
				}
				em.merge(tea);
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
	public boolean delete(Profesor tea) throws ConnectionException {
		EntityManager em = this.getEntityManager();

		try {
			em.getTransaction().begin();
			if(tea.getId() == 1) {
				throw new ConnectionException(ConnectionError.CANT_DELETE_ADMIN);
			}
			em.merge(tea);
			for(Aula cla: tea.getAulas()) {
				cla.getProfesors().remove(tea);
				em.merge(cla);
			}
			em.remove(em.contains(tea) ? tea : em.merge(tea));
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

	private boolean checkFields(Profesor tea) throws ConnectionException {

		if (tea.getNombre().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (tea.getNombre().length() > 50) {
			throw new ConnectionException(ConnectionError.NAME_TOO_LONG);
		} else if (super.namePattern.matcher(tea.getNombre()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_NAME);
		}

		if (tea.getApellido1().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (tea.getApellido1().length() > 75 || tea.getApellido2().length() > 75) {
			throw new ConnectionException(ConnectionError.SURNAME_TOO_LONG);
		} else if (super.namePattern.matcher(tea.getApellido1()).find()
				|| super.namePattern.matcher(tea.getApellido2()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_SURNAME);
		}

		if (tea.getNif().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (tea.getNif().length() != 9 || super.nifPattern.matcher(tea.getNif()).find()
				|| super.noNumberPattern.matcher(tea.getNif().substring(0, 7)).find()
				|| super.noCapsLetterPattern.matcher(tea.getNif().substring(8, 8)).find()) {
			throw new ConnectionException(ConnectionError.WRONG_NIF);
		}

		if (tea.getContrasena().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (tea.getContrasena().length() > 64 || tea.getContrasena().length() < 8) {
			throw new ConnectionException(ConnectionError.WRONG_PASSWORD_LENGTH);
		} else if(super.passPattern.matcher(tea.getContrasena()).find()) {
			throw new ConnectionException(ConnectionError.WRONF_PASSWORD);
		}

		if (tea.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}

		return true;
	}

}
