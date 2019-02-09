package io;

import java.io.FileNotFoundException;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

public interface IOControl {

	public boolean exportData();

	public boolean importData();
	
	public List<Alumno> readStudentsCSV(String path) throws FileNotFoundException;
	
	public List<Profesor> readTeachersCSV(String path) throws FileNotFoundException;
	
	public List<Aula> readClassroomsCSV(String path) throws FileNotFoundException;
	
	public List<Evaluacion> readEvaluationsCSV(String path) throws FileNotFoundException;
	
	public List<Puntuacion> readPuntuationsCSV(String path) throws FileNotFoundException;

}
