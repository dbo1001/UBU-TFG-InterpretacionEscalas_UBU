package io;

import java.io.FileNotFoundException;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

/**
 * Interfaz para acceder al paquete io
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public interface IOControl {

	/**
	 * Exporta a csv y sube dichos csv a la nube
	 * @return éxito o fracaso
	 */
	public boolean exportData();

	/**
	 * Descarga los csv de la nube
	 * @return éxito o fracaso
	 */
	public boolean importData();
	
	/**
	 * Lee el fichero csv que contiene los alumnos
	 * @param path ruta del fichero
	 * @return alumnos recuperados
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public List<Alumno> readStudentsCSV(String path) throws FileNotFoundException;
	
	/**
	 * Lee el fichero csv que contiene los profesores
	 * @param path ruta del fichero
	 * @return profesores recuperados
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public List<Profesor> readTeachersCSV(String path) throws FileNotFoundException;
	
	/**
	 * Lee el fichero csv que contiene las aulas
	 * @param path ruta del fichero
	 * @return aulas recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public List<Aula> readClassroomsCSV(String path) throws FileNotFoundException;
	
	/**
	 * Lee el fichero csv que contiene las evaluaciones de un aula
	 * @param path ruta del fichero
	 * @return evaluaciones recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public List<Evaluacion> readEvaluationsCSV(String path) throws FileNotFoundException;
	
	/**
	 * Lee el fichero csv que contiene las puntuaciones de un aula
	 * @param path ruta del fichero
	 * @return puntuaciones recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public List<Puntuacion> readPuntuationsCSV(String path) throws FileNotFoundException;

}
