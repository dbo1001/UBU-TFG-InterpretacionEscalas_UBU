package service;

import java.util.ArrayList;
import java.util.List;

import model.Alumno;

public class StudentServiceImpl implements Service<Alumno> {
	
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
		
		return resultado;
	}
	
	@Override
	public boolean edit(long id) {
		System.err.println("Transaccion no implementada. Alumno: " + id);
		return false;
	}

	@Override
	public boolean delete(long id) {
		System.err.println("Transaccion no implementada. Alumno: " + id);
		return false;
	}

}
