package connection.manageService;

import java.util.ArrayList;
import java.util.List;

import connection.ConnectionException;
import connection.ServiceImpl;
import model.Alumno;
import model.Aula;
import model.Profesor;

public class TeacherServiceImpl extends ServiceImpl implements ManageService<Profesor> {

	@Override
	public List<Profesor> getAll() {
		
		//Codigo de ejemplo usado para testear
		
		List<Profesor> resultado = new ArrayList<Profesor>();
		Profesor profesor1 = new Profesor();
		Profesor profesor2 = new Profesor();
		Profesor profesor3 = new Profesor();
		Profesor profesor4 = new Profesor();
		Profesor profesor5 = new Profesor();
		
		profesor1.setId(1);
		profesor1.setNombre("Gonzalo");
		profesor1.setApellido1("Garcia");
		profesor1.setApellido2("Sanchez");
		
		profesor2.setId(2);
		profesor2.setNombre("Ester");
		profesor2.setApellido1("Ruiz");
		profesor2.setApellido2("Mu�oz");
		
		profesor3.setId(3);
		profesor3.setNombre("Fernando");
		profesor3.setApellido1("Alonso");
		profesor3.setApellido2("Alvarez");
		
		profesor4.setId(4);
		profesor4.setNombre("Maria");
		profesor4.setApellido1("Romero");
		profesor4.setApellido2("Navarro");
		
		profesor5.setId(5);
		profesor5.setNombre("Clara");
		profesor5.setApellido1("Moreno");
		profesor5.setApellido2("Vazquez");
		
		resultado.add(profesor1);
		resultado.add(profesor2);
		resultado.add(profesor3);
		resultado.add(profesor4);
		resultado.add(profesor5);
		
		
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
	public boolean add(Profesor object) throws ConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Profesor object) throws ConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Profesor tea) {
		System.err.println("Transaccion no implementada. Profesor: " + tea.getId());
		return false;
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
		al.setApellido1("Cort�s");
		al.setNombre("Hernan");
		tea.setAulas(cList);
		
		return tea;
	}

}
