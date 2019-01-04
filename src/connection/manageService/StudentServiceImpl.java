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

public class StudentServiceImpl extends ServiceImpl implements ManageService<Alumno> {
	
	@Override
	public List<Alumno> getAll() {
		
		//Codigo de ejemplo usado para testear
		
		List<Alumno> resultado = new ArrayList<Alumno>();
		Alumno alumno1 = new Alumno();
		Alumno alumno2 = new Alumno();
		Alumno alumno3 = new Alumno();
		Alumno alumno4 = new Alumno();
		Alumno alumno5 = new Alumno();
		
		alumno1.setId(1);
		alumno1.setNombre("Alvar");
		alumno1.setApellido1("Mendez");
		alumno1.setApellido2("Domingo");
		
		alumno2.setId(2);
		alumno2.setNombre("Barvara");
		alumno2.setApellido1("Rodriguez");
		alumno2.setApellido2("Gonzalez");
		
		alumno3.setId(3);
		alumno3.setNombre("Carlos");
		alumno3.setApellido1("de la Horra");
		alumno3.setApellido2("Herrero");
		
		alumno4.setId(4);
		alumno4.setNombre("Daniel");
		alumno4.setApellido1("Gutierrez");
		alumno4.setApellido2("Domador");
		
		alumno5.setId(5);
		alumno5.setNombre("Esteban");
		alumno5.setApellido1("Perez");
		alumno5.setApellido2("Santamaria");
		
		resultado.add(alumno1);
		resultado.add(alumno2);
		resultado.add(alumno3);
		resultado.add(alumno4);
		resultado.add(alumno5);
		
		
		//Este bucle crea relleno para mostrar la scroll bar
		/*
		for(int i = 0; i<100;i++) {
			Alumno fill = new Alumno();
			fill.setNombre("fill");
			fill.setApellido1("fill");
			fill.setApellido2("fill");
			resultado.add(fill);
		}*/
		
		return resultado;
	}
	
	@Override
	public boolean add(Alumno stu) throws ConnectionException {
		
		this.checkFields(stu);
		
		EntityManager em = this.getEntityManager();
		
		try {
			em.getTransaction().begin();
			this.getEntityManager().persist(stu);
		}catch(Exception ex) {
			
			em.getTransaction().rollback();
			throw ex;
		}finally {
			
			if(em.isOpen()) {
				em.close();
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

	private void checkFields(Alumno stu) throws ConnectionException {
		
		if (stu.getNombre() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}else if (stu.getNombre().length() > 50){
			throw new ConnectionException(ConnectionError.NAME_TOO_LONG);
		} else if(super.namePattern.matcher(stu.getNombre()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_NAME);
		}
		
		if (stu.getApellido1() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}else if (stu.getApellido1().length() > 75 || stu.getApellido2().length() > 75){
			throw new ConnectionException(ConnectionError.SURNAME_TOO_LONG);
		} else if(super.namePattern.matcher(stu.getApellido1()).find() || super.namePattern.matcher(stu.getApellido2()).find()) {
			throw new ConnectionException(ConnectionError.WRONG_SURNAME);
		}
		
		if (stu.getNif() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}
		
		if (stu.getFechaNacimiento() == null) {
			throw new ConnectionException(ConnectionError.FIELD_IS_EMPTY);
		}else if (stu.getFechaNacimiento().after(Calendar.getInstance().getTime())){
			throw new ConnectionException(ConnectionError.WRONG_DATE);
		} 
		
		if(stu.getDireccion() != null && stu.getDireccion().length() > 150) {
			throw new ConnectionException(ConnectionError.DIRECTION_TOO_LONG);
		}
		
		if(stu.getNotas() != null && stu.getNotas().length() > 1000) {
			throw new ConnectionException(ConnectionError.DESCRIPTION_TOO_LONG);
		}
	}

}
