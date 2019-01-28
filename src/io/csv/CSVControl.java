package io.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
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

}
