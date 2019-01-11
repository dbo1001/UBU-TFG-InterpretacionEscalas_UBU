package connection.manageService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Alumno;
import model.Categorizacion;
import model.Profesor;

public class StudentServiceImpl extends ServiceImpl implements ManageService<Alumno> {

	@Override
	public List<Alumno> getAll() {

		return getEntityManager().createNamedQuery("Alumno.findAll", Alumno.class).getResultList();
	}

	@Override
	public boolean add(Alumno stu) throws ConnectionException {

		if (this.checkFields(stu)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				em.persist(stu);
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
	public boolean edit(Alumno stu) throws ConnectionException {
		if (this.checkFields(stu)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				em.merge(stu);
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
	public boolean delete(Alumno stu) throws ConnectionException {

		EntityManager em = this.getEntityManager();

		try {
			em.getTransaction().begin();
			em.remove(em.contains(stu) ? stu : em.merge(stu));
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

	private boolean checkFields(Alumno stu) throws ConnectionException {
		
		if (stu.getNombre().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getNombre().length() > 50) {
			throw new ConnectionException(ConnectionError.NAME_TOO_LONG);
		} else if (super.namePattern.matcher(stu.getNombre()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_NAME);
		}

		if (stu.getApellido1().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getApellido1().length() > 75 || stu.getApellido2().length() > 75) {
			throw new ConnectionException(ConnectionError.SURNAME_TOO_LONG);
		} else if (super.namePattern.matcher(stu.getApellido1()).find()
				|| super.namePattern.matcher(stu.getApellido2()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_SURNAME);
		}

		if (stu.getCodigo().equals("")) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}else if (stu.getCodigo().length() > 30) {
			throw new ConnectionException(ConnectionError.WRONG_CODE);
		}

		if (stu.getFechaNacimiento() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getFechaNacimiento().after(Calendar.getInstance().getTime())) {
			throw new ConnectionException(ConnectionError.WRONG_DATE);
		}

		if (stu.getDireccion().length() > 150) {
			throw new ConnectionException(ConnectionError.DIRECTION_TOO_LONG);
		}

		if (stu.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}
		
		if(stu.getAula().getAlumnos().size() >= stu.getAula().getCapacidad()) {
			throw new ConnectionException(ConnectionError.CLASSROOM_IS_FULL);
		}
		
		return true;
	}

}
