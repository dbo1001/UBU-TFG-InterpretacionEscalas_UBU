package io;

import java.io.IOException;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;

public interface IOControl {

	public boolean exportData() throws IOException;

	public boolean importData();
	
	public List<Alumno> readStudentsCSV(String path);
	
	public List<Profesor> readTeachersCSV(String path);
	
	public List<Aula> readClassroomsCSV(String path);
	
	public List<Evaluacion> readEvaluationsCSV(String path);

}
