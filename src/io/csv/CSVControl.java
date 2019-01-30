package io.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

public abstract class CSVControl {
	
	public static void exportStudents(List<Alumno> students) throws IOException {
		String csvPath = "ioData/alumnos.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Alumno stu: students) {
			CSVUtil.writeStudent(writer, stu);
		}
		
		writer.flush();
		writer.close();
	}
	
	public static void exportTeachers(List<Profesor> teachers) throws IOException {
		String csvPath = "ioData/profesores.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Profesor tea: teachers) {
			CSVUtil.writeTeacher(writer, tea);
		}
		
		writer.flush();
		writer.close();
	}
	
	public static void exportClassroom(List<Aula> classrooms) throws IOException {
		String csvPath = "ioData/aulas.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Aula cla: classrooms) {
			CSVUtil.writeClassroom(writer, cla);
		}
		
		writer.flush();
		writer.close();
	}
	
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
	
	private static void exportPuntuation(Set<Puntuacion> puntuations, String path) throws IOException {
		String csvPath = path + "puntuaciones.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Puntuacion pun: puntuations) {
			CSVUtil.writePuntuation(writer, pun);
		}
		
		writer.flush();
		writer.close();
	}
	
	public static void createClassroomFile (Aula cla) {
		File sourceFile = new File("ioData/Evaluaciones");
		File claFile = new File("iodata/Evaluaciones/"+cla.getNombre());
		sourceFile.mkdir();
		claFile.mkdir();
	}
	
	public static List<Alumno> readStudentsCSV(String path) throws FileNotFoundException {
		List<Alumno> stus = new ArrayList<Alumno>();
		Calendar cal = Calendar.getInstance();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Alumno stu = new Alumno();
			Iterator<String> it = line.iterator();
			stu.setId(Integer.parseInt(it.next()));
			stu.setCodigo(it.next());
			String fecha = it.next();
			//System.out.println(Integer.parseInt(fecha.substring(0, 4))+","+ Integer.parseInt(fecha.substring(5,7))+","+ Integer.parseInt(fecha.substring(8,10)));
			cal.set(Integer.parseInt(fecha.substring(0, 4)), Integer.parseInt(fecha.substring(5,7)) - 1, Integer.parseInt(fecha.substring(8,10)));
			stu.setFechaNacimiento(cal.getTime());
			stu.setNombre(it.next());
			stu.setApellido1(it.next());
			stu.setApellido2(it.next());
			stu.setDireccion(it.next());
			stu.setNotas(it.next());
			Aula cla = new Aula();
			cla.setId(Integer.parseInt(it.next()));
			stu.setAula(cla);
			
			stus.add(stu);
			
			//System.out.println(stu.getId()+","+stu.getCodigo()+","+stu.getFechaNacimiento()+","+stu.getNombre()+","+stu.getApellido1()+","+stu.getApellido2()+","+stu.getDireccion()+","+stu.getNotas()+","+stu.getAula().getId());
			
		}
		
		return stus;
	}
	
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
	
	public static List<Aula> readClassroomsCSV(String path) throws FileNotFoundException {
		List<Aula> classrooms = new ArrayList<Aula>();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Aula cla = new Aula();
			Iterator<String> it = line.iterator();
			cla.setId(Integer.parseInt(it.next()));
			cla.setNombre(it.next());
			cla.setCapacidad(Integer.parseInt(it.next()));
			cla.setNotas(it.next());
			
			System.out.println(cla.getNombre());
			
		}
		
		return classrooms;
	}
	
	public static List<Evaluacion> readEvaluationsCSV(String path) throws FileNotFoundException {
		List<Evaluacion> evaluations = new ArrayList<Evaluacion>();
		Calendar cal = Calendar.getInstance();
		
		for(List<String> line : CSVUtil.readCSVFile(path)) {
			Evaluacion eva = new Evaluacion();
			Iterator<String> it = line.iterator();
			eva.setId(Integer.parseInt(it.next()));
			String fecha = it.next();
			//System.out.println(Integer.parseInt(fecha.substring(0, 4))+","+ Integer.parseInt(fecha.substring(5,7))+","+ Integer.parseInt(fecha.substring(8,10)));
			cal.set(Integer.parseInt(fecha.substring(0, 4)), Integer.parseInt(fecha.substring(5,7)) - 1, Integer.parseInt(fecha.substring(8,10)));
			eva.setFecha(new Timestamp(cal.getTime().getTime()));
			Alumno stu = new Alumno();
			stu.setId(Integer.parseInt(it.next()));
			eva.setAlumno(stu);
			
		}
		
		return evaluations;
	}
	
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
			eva.setId(Integer.parseInt(it.next()));
			pun.setEvaluacion(eva);
			
		}
		
		return puntuations;
	}

}
