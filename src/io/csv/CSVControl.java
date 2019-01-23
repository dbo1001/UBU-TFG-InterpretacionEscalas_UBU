package io.csv;

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

public class CSVControl {
	
	// TODO borrar
	public void test () throws IOException {
		String csvPath = "ioData/test.csv";
		FileWriter writer = new FileWriter(csvPath);
		
		CSVUtil.writeLine(writer, Arrays.asList("aaa","bbb", "cc,c"));
		writer.flush();
		writer.close();
	}
	
	public void exportStudents(List<Alumno> students) throws IOException {
		String csvPath = "ioData/alumnos.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Alumno stu: students) {
			CSVUtil.writeStudent(writer, stu);
		}
		
		writer.flush();
		writer.close();
	}
	
	public void exportTeachers(List<Profesor> teachers) throws IOException {
		String csvPath = "ioData/profesores.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Profesor tea: teachers) {
			CSVUtil.writeTeacher(writer, tea);
		}
		
		writer.flush();
		writer.close();
	}
	
	public void exportClassroom(List<Aula> classrooms) throws IOException {
		String csvPath = "ioData/aulas.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Aula cla: classrooms) {
			CSVUtil.writeClassroom(writer, cla);
		}
		
		writer.flush();
		writer.close();
	}
	
	public void exportEvaluation(List<Evaluacion> evaluations) throws IOException {
		String csvPath = "ioData/evaluaciones.csv";
		FileWriter writer = new FileWriter(csvPath);
		Set<Puntuacion> puntuations = new HashSet<Puntuacion>();
		for(Evaluacion eva: evaluations) {
			puntuations.addAll(eva.getPuntuacions());
			CSVUtil.writeEvaluation(writer, eva);
		}
		
		writer.flush();
		writer.close();
		this.exportPuntuation(puntuations);
	}
	
	private void exportPuntuation(Set<Puntuacion> puntuations) throws IOException {
		String csvPath = "ioData/puntuaciones.csv";
		FileWriter writer = new FileWriter(csvPath);
		for(Puntuacion pun: puntuations) {
			CSVUtil.writePuntuation(writer, pun);
		}
		
		writer.flush();
		writer.close();
	}

}
