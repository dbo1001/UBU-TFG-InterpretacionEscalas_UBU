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
				//TODO borrar esta linea y hacer que la id se genere por sequence
				stu.setId(1);
				this.getEntityManager().persist(stu);
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
	public boolean edit(Alumno object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		System.err.println("Transaccion no implementada. Alumno: " + id);
		return false;
	}

	private boolean checkFields(Alumno stu) throws ConnectionException {
		
		if (stu.getNombre() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getNombre().length() > 50) {
			throw new ConnectionException(ConnectionError.NAME_TOO_LONG);
		} else if (super.namePattern.matcher(stu.getNombre()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_NAME);
		}

		if (stu.getApellido1() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getApellido1().length() > 75 || stu.getApellido2().length() > 75) {
			throw new ConnectionException(ConnectionError.SURNAME_TOO_LONG);
		} else if (super.namePattern.matcher(stu.getApellido1()).find()
				|| super.namePattern.matcher(stu.getApellido2()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_SURNAME);
		}

		if (stu.getNif() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}

		if (stu.getFechaNacimiento() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		} else if (stu.getFechaNacimiento().after(Calendar.getInstance().getTime())) {
			throw new ConnectionException(ConnectionError.WRONG_DATE);
		}

		if (stu.getDireccion() != null && stu.getDireccion().length() > 150) {
			throw new ConnectionException(ConnectionError.DIRECTION_TOO_LONG);
		}

		if (stu.getNotas() != null && stu.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}
		
		if(stu.getAula().getAlumnos().size() >= stu.getAula().getCapacidad()) {
			throw new ConnectionException(ConnectionError.CLASSROOM_IS_FULL);
		}
		
		return true;
	}

}
