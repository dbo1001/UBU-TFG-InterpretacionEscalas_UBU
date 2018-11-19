package service;

import java.util.List;

import model.Alumno;
import model.Aula;
import model.Profesor;

public interface Service {
	
	public List<Alumno> getStudents();
	
	public List<Profesor> getTeachers();
	
	public List<Aula> getClassrooms();
	
	public boolean editStudent(long id);
	
	public boolean deleteStudent(long id);
	

}
