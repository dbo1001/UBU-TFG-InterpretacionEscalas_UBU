package connection.manageService;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Alumno;
import model.Aula;
import model.Profesor;

public class TeacherServiceImpl extends ServiceImpl implements ManageService<Profesor> {

	@Override
	public List<Profesor> getAll() {
		return getEntityManager().createNamedQuery("Profesor.findAll", Profesor.class).getResultList();

		/*
		 * //Codigo de ejemplo usado para testear
		 * 
		 * List<Profesor> resultado = new ArrayList<Profesor>(); Profesor profesor1 =
		 * new Profesor(); Profesor profesor2 = new Profesor(); Profesor profesor3 = new
		 * Profesor(); Profesor profesor4 = new Profesor(); Profesor profesor5 = new
		 * Profesor();
		 * 
		 * profesor1.setId(1); profesor1.setNombre("Gonzalo");
		 * profesor1.setApellido1("Garcia"); profesor1.setApellido2("Sanchez");
		 * 
		 * profesor2.setId(2); profesor2.setNombre("Ester");
		 * profesor2.setApellido1("Ruiz"); profesor2.setApellido2("Muñoz");
		 * 
		 * profesor3.setId(3); profesor3.setNombre("Fernando");
		 * profesor3.setApellido1("Alonso"); profesor3.setApellido2("Alvarez");
		 * 
		 * profesor4.setId(4); profesor4.setNombre("Maria");
		 * profesor4.setApellido1("Romero"); profesor4.setApellido2("Navarro");
		 * 
		 * profesor5.setId(5); profesor5.setNombre("Clara");
		 * profesor5.setApellido1("Moreno"); profesor5.setApellido2("Vazquez");
		 * 
		 * resultado.add(profesor1); resultado.add(profesor2); resultado.add(profesor3);
		 * resultado.add(profesor4); resultado.add(profesor5);
		 * 
		 * 
		 * //Este bucle crea relleno para mostrar la scroll bar /* for(int i = 0;
		 * i<100;i++) { Alumno fill = new Alumno(); fill.setNombre("fill");
		 * fill.setApellido1("fill"); fill.setApellido2("fill"); resultado.add(fill); }
		 * 
		 * return resultado;
		 */
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

	public Profesor getCurrentteacher() {
		Profesor tea = new Profesor();
		Aula classroom = new Aula();
		Aula classroom2 = new Aula();
		Alumno al = new Alumno();
		List<Alumno> aList = new ArrayList<Alumno>();
		List<Aula> cList = new ArrayList<Aula>();
		aList.add(al);
		ManageService<Alumno> mS = new StudentServiceImpl();
		tea.setId(1);
		tea.setNombre("Gonzalo");
		tea.setApellido1("Garcia");
		tea.setApellido2("Sanchez");
		classroom.setNombre("Aula1");
		classroom.setCapacidad(1);
		classroom.setAlumnos(mS.getAll());
		classroom2.setNombre("Aula2");
		classroom2.setCapacidad(2);
		classroom2.setAlumnos(aList);
		cList.add(classroom);
		cList.add(classroom2);
		al.setApellido1("Cortés");
		al.setNombre("Hernan");
		tea.setAulas(cList);

		return tea;
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
		}

		if (tea.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}

		return true;
	}

}
