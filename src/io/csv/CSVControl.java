package io.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Item;
import model.Profesor;
import model.Puntuacion;

/**
 * Clase que contiene la lógica necesaria para manejar los archivos csv
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public abstract class CSVControl {
	
	/**
	 * Crea el directorio raiz "ioData" donde están los archivos csv
	 * @throws IOException no se ha podido crear el directorio
	 */
	public static void createRootDirectory() throws IOException {
		File root = new File("ioData");
		root.mkdir();
	}
	
	/**
	 * Crea el archivo csv de los alumnos
	 * @param students alumnos a exportar
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	public static void exportStudents(List<Alumno> students) throws IOException {
		String csvPath = "ioData/alumnos.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Alumno stu: students) {
			CSVUtil.writeStudent(writer, stu);
		}
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Crea el fichero csv que contiene los profesores
	 * @param teachers profesores a exportar
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	public static void exportTeachers(List<Profesor> teachers) throws IOException {
		String csvPath = "ioData/profesores.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Profesor tea: teachers) {
			CSVUtil.writeTeacher(writer, tea);
		}
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Crea el fichero csv que contiene las aulas
	 * @param classrooms aulas a exportar
	 * @throws IOException no se ha podido crear o sobreescribir el fichero
	 */
	public static void exportClassroom(List<Aula> classrooms) throws IOException {
		String csvPath = "ioData/aulas.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Aula cla: classrooms) {
			CSVUtil.writeClassroom(writer, cla);
		}
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Crea el fichero csv con las evaluaciones del aula indicada
	 * @param evaluations evaluaciones a exportar
	 * @param path path donde se exportarán, indicando el nombre del aula
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	public static void exportEvaluation(List<Evaluacion> evaluations, String path) throws IOException {
		String csvPath = path + "evaluaciones.csv";
		FileWriter writer = new FileWriter(csvPath);
		Set<Puntuacion> puntuations = new HashSet<Puntuacion>();
		for(Evaluacion eva: evaluations) {
			puntuations.addAll(eva.getPuntuacions());
			CSVUtil.writeEvaluation(writer, eva);
		}
		
		writer.flush();
		writer.close();
		CSVControl.exportPuntuation(puntuations, path);
	}
	
	/**
	 * Crea el fichero csv con las puntuaciones del aula indicada
	 * @param puntuations puntuaciones a exportar
	 * @param path path donde se exportarán, indicando el nombre del aula
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	private static void exportPuntuation(Set<Puntuacion> puntuations, String path) throws IOException {
		String csvPath = path + "puntuaciones.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Puntuacion pun: puntuations) {
			CSVUtil.writePuntuation(writer, pun);
		}
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * Crea el directorio donde se almacenaran los csv de las evaluaciones
	 * y puntuaciones de los alumnos pertenecientes a dicha aula
	 * @param cla aula
	 */
	public static void createClassroomFile (Aula cla) {
		File sourceFile = new File("ioData/Evaluaciones");
		File claFile = new File("iodata/Evaluaciones/"+cla.getNombre());
		sourceFile.mkdir();
		claFile.mkdir();
	}
	
	/**
	 * Lee el archivo csv que contiene los alumnos
	 * @param path ruta del archivo
	 * @return alumnos recuperados
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public static List<Alumno> readStudentsCSV(String path) throws FileNotFoundException {
		List<Alumno> stus = new ArrayList<Alumno>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Alumno stu = new Alumno();
			Iterator<String> it = line.iterator();
			stu.setId(Integer.parseInt(it.next()));
			stu.setCodigo(it.next());
			stu.setFechaNacimiento(new Timestamp(Long.parseLong(it.next())));
			stu.setNombre(it.next());
			stu.setApellido1(it.next());
			stu.setApellido2(it.next());
			stu.setDireccion(it.next());
			stu.setNotas(it.next());
			Aula cla = new Aula();
			cla.setNombre(it.next());
			stu.setAula(cla);
			
			stus.add(stu);
			
			//System.out.println(stu.getId()+","+stu.getCodigo()+","+stu.getFechaNacimiento()+","+stu.getNombre()+","+stu.getApellido1()+","+stu.getApellido2()+","+stu.getDireccion()+","+stu.getNotas()+","+stu.getAula().getId());
			
		}
		
		return stus;
	}
	
	/**
	 * Lee el archivo csv que contiene los profesores
	 * @param path ruta del archivo
	 * @return profesores recuperados
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public static List<Profesor> readTeachersCSV(String path) throws FileNotFoundException {
		List<Profesor> teachers = new ArrayList<Profesor>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Profesor tea = new Profesor();
			Iterator<String> it = line.iterator();
			tea.setId(Integer.parseInt(it.next()));
			tea.setNif(it.next());
			tea.setNombre(it.next());
			tea.setApellido1(it.next());
			tea.setApellido2(it.next());
			tea.setNotas(it.next());
			if(it.next().equals("true")) {
				tea.setPermisos(true);
			}else {
				tea.setPermisos(false);
			}
			tea.setContrasena(it.next());
			
			
			teachers.add(tea);
			
		}
		
		return teachers;
	}
	
	/**
	 * Lee el archivo csv que contiene las aulas
	 * @param path ruta del archivo
	 * @return aulas recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public static List<Aula> readClassroomsCSV(String path) throws FileNotFoundException {
		List<Aula> classrooms = new ArrayList<Aula>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Aula cla = new Aula();
			Iterator<String> it = line.iterator();
			cla.setId(Integer.parseInt(it.next()));
			cla.setNombre(it.next());
			cla.setCapacidad(Integer.parseInt(it.next()));
			cla.setNotas(it.next());
			while(it.hasNext()) {
				Profesor tea = new Profesor();
				tea.setNif(it.next());
				cla.getProfesors().add(tea);
			}
			
			classrooms.add(cla);
		}
		
		return classrooms;
	}
	
	
	/**
	 * Lee el archivo csv que contiene las evaluaciones
	 * @param path ruta del archivo
	 * @return evaluaciones recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public static List<Evaluacion> readEvaluationsCSV(String path) throws FileNotFoundException {
		List<Evaluacion> evaluations = new ArrayList<Evaluacion>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Evaluacion eva = new Evaluacion();
			Iterator<String> it = line.iterator();
			eva.setId(Integer.parseInt(it.next()));
			eva.setFecha(new Timestamp(Long.parseLong(it.next())));
			Alumno stu = new Alumno();
			stu.setCodigo(it.next());
			eva.setAlumno(stu);
			
			evaluations.add(eva);
			
		}
		
		return evaluations;
	}
	
	/**
	 * Lee el archivo csv que contiene las puntuaciones
	 * @param path ruta del archivo
	 * @return puntuaciones recuperadas
	 * @throws FileNotFoundException archivo no encontrado
	 */
	public static List<Puntuacion> readPuntuationsCSV(String path) throws FileNotFoundException {
		List<Puntuacion> puntuations = new ArrayList<Puntuacion>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Puntuacion pun = new Puntuacion();
			Iterator<String> it = line.iterator();
			pun.setId(Integer.parseInt(it.next()));
			pun.setValoracion(Integer.parseInt(it.next()));
			Item item = new Item();
			item.setNumero(Integer.parseInt(it.next()));
			pun.setItem(item);
			Evaluacion eva = new Evaluacion();
			eva.setFecha(new Timestamp(Long.parseLong(it.next())));;
			pun.setEvaluacion(eva);
			
			puntuations.add(pun);
			
		}
		
		return puntuations;
	}

}
