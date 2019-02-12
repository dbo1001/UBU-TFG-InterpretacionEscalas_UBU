package io.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

/**
 * Clase con las utilidades básicas para manejar archivos csv
 * @author Mario Núñez Izquierdo
 * @version 1.0
 *
 */
public class CSVUtil {

	private static final char DEFAULT_SEPARATOR = ',';

	/**
	 * Introduce una linea en uncsv
	 * @param writer writer que escribe en el csv
	 * @param content contenido de la linea
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeLine(Writer writer, List<String> content) throws IOException {
		writeLine(writer, content, DEFAULT_SEPARATOR);
	}

	/**
	 * Modifica la cadena para que tenga formato csv
	 * @param str cadena a modificar
	 * @return cadena resultante
	 */
	private static String followCSVformat(String str) {
		String result = str;

		if (result.contains("\""))
			result = result.replace("\"", "\"\"");

		return result;
	}

	/**
	 * Escribe una linea en el csv
	 * @param writer writer que escribe en el csv
	 * @param content contenido de la linea
	 * @param separators separador personalizado
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeLine(Writer writer, List<String> content, char separators) throws IOException {

		boolean first = true;

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();

		for (String str : content) {

			if (!first)
				sb.append(separators);
			sb.append(followCSVformat(str));
			first = false;

		}

		sb.append("\n");
		writer.append(sb.toString());
	}

	/**
	 * Escribe en un csv una linea con los datos del alumno
	 * @param writer writer que escribe en el csv
	 * @param stu alumno
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeStudent(Writer writer, Alumno stu) throws IOException {
		List<String> stuToCsv = new ArrayList<String>();
		stuToCsv.add(""+stu.getId());
		stuToCsv.add(stu.getCodigo());
		stuToCsv.add(""+stu.getFechaNacimiento().getTime());
		stuToCsv.add(stu.getNombre());
		stuToCsv.add(stu.getApellido1());
		stuToCsv.add(stu.getApellido2());
		stuToCsv.add(stu.getDireccion());
		stuToCsv.add(stu.getNotas());
		stuToCsv.add(""+stu.getAula().getNombre());
		CSVUtil.writeLine(writer, stuToCsv);

	}

	/**
	 * Escribe en un csv una linea con los datos del profesor
	 * @param writer writer que escribe en el csv
	 * @param tea profesor
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeTeacher(Writer writer, Profesor tea) throws IOException {
		List<String> teaToCsv = new ArrayList<String>();
		teaToCsv.add(""+tea.getId());
		teaToCsv.add(tea.getNif());
		teaToCsv.add(tea.getNombre());
		teaToCsv.add(tea.getApellido1());
		teaToCsv.add(tea.getApellido2());
		teaToCsv.add(tea.getNotas());
		teaToCsv.add(tea.getPermisos().toString());
		teaToCsv.add(tea.getContrasena());
		CSVUtil.writeLine(writer, teaToCsv);
	}

	/**
	 * Escribe en un csv una linea con los datos del aula
	 * @param writer writer que escribe en el csv
	 * @param cla aula
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeClassroom(Writer writer, Aula cla) throws IOException {
		List<String> claToCsv = new ArrayList<String>();
		claToCsv.add(""+cla.getId());
		claToCsv.add(cla.getNombre());
		claToCsv.add(""+cla.getCapacidad());
		claToCsv.add(cla.getNotas());
		for(Profesor tea: cla.getProfesors()) {
			claToCsv.add(""+tea.getNif());
		}
		CSVUtil.writeLine(writer, claToCsv);
	}
	
	/**
	 * Escribe en un csv una linea con los datos de la puntuación
	 * @param writer writer que escribe en el csv
	 * @param pun puntuación
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writePuntuation(Writer writer, Puntuacion pun) throws IOException {
		List<String> punToCsv = new ArrayList<String>();
		punToCsv.add(""+pun.getId());
		punToCsv.add(""+pun.getValoracion());
		punToCsv.add(""+pun.getItem().getNumero());
		punToCsv.add(""+pun.getEvaluacion().getFecha().getTime());
		CSVUtil.writeLine(writer, punToCsv);
	}
	
	/**
	 * Escribe en un csv una linea con los datos de la evaluación
	 * @param writer writer que escribe en el csv
	 * @param eva evaluación
	 * @throws IOException no se ha podido crear o sobreescribir el archivo
	 */
	protected static void writeEvaluation(Writer writer, Evaluacion eva) throws IOException {
		List<String> evaToCsv = new ArrayList<String>();
		evaToCsv.add(""+eva.getId());
		evaToCsv.add(""+eva.getFecha().getTime());
		evaToCsv.add(eva.getAlumno().getCodigo());
		CSVUtil.writeLine(writer, evaToCsv);
	}
	
	/**
	 * Lee un archivo csv
	 * @param path ruta al archivo
	 * @return lista con las lineas del csv, cada linea es otra lista con los datos contenidos en forma de String
	 * @throws FileNotFoundException arhivo no encontrado
	 */
	protected static List<List<String>> readCSVFile(String path) throws FileNotFoundException{
		List<List<String>> file = new ArrayList<List<String>>();
		Scanner scanner = new Scanner(new File(path));
		
		while(scanner.hasNext()) {
			file.add(CSVUtil.parseLine(scanner.nextLine()));
		}
		
		scanner.close();
		
		return file;
	}
	
	/**
	 * Transforma una linea del csv en una lista con los diferentes
	 *  campos que hay en esa linea separados por el separador (,)
	 * @param nextLine linea a parsear
	 * @return lista con los campos en forma de Strings
	 */
	private static List<String> parseLine(String nextLine) {
		List<String> result = new ArrayList<String>();
		
		if(nextLine == null || nextLine.isEmpty()) {
			return result;
		}
		
		char[] chars = nextLine.toCharArray();
		StringBuffer curVal = new StringBuffer();
		
		for(char ch : chars) {
			if(ch != DEFAULT_SEPARATOR) {
				curVal.append(ch);
			}else {
				result.add(curVal.toString());
				curVal.delete(0, curVal.length());
			}
		}
		result.add(curVal.toString());
		
		return result;
	}

}
