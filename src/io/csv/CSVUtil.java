package io.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

public class CSVUtil {

	private static final char DEFAULT_SEPARATOR = ',';

	protected static void writeLine(Writer writer, List<String> content) throws IOException {
		writeLine(writer, content, DEFAULT_SEPARATOR);
	}

	private static String followCSVformat(String str) {
		String result = str;

		if (result.contains("\""))
			result = result.replace("\"", "\"\"");

		return result;
	}

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

	protected static void writeStudent(Writer writer, Alumno stu) throws IOException {
		List<String> stuToCsv = new ArrayList<String>();
		stuToCsv.add(stu.getCodigo());
		stuToCsv.add(stu.getFechaNacimiento().toString());
		stuToCsv.add(stu.getNombre());
		stuToCsv.add(stu.getApellido1());
		stuToCsv.add(stu.getApellido2());
		stuToCsv.add(stu.getDireccion());
		stuToCsv.add(stu.getNotas());
		stuToCsv.add(stu.getAula().getNombre());
		CSVUtil.writeLine(writer, stuToCsv);

	}

	protected static void writeTeacher(Writer writer, Profesor tea) throws IOException {
		List<String> teaToCsv = new ArrayList<String>();
		teaToCsv.add(tea.getNif());
		teaToCsv.add(tea.getNombre());
		teaToCsv.add(tea.getApellido1());
		teaToCsv.add(tea.getApellido2());
		teaToCsv.add(tea.getNotas());
		teaToCsv.add(tea.getPermisos().toString());
		teaToCsv.add(tea.getContrasena());
		CSVUtil.writeLine(writer, teaToCsv);
	}

	protected static void writeClassroom(Writer writer, Aula cla) throws IOException {
		List<String> claToCsv = new ArrayList<String>();
		claToCsv.add(cla.getNombre());
		claToCsv.add(""+cla.getCapacidad());
		claToCsv.add(cla.getNotas());
		for(Profesor tea: cla.getProfesors()) {
			claToCsv.add(tea.getNif());
		}
		CSVUtil.writeLine(writer, claToCsv);
	}
	
	protected static void writePuntuation(Writer writer, Puntuacion pun) throws IOException {
		List<String> punToCsv = new ArrayList<String>();
		punToCsv.add(""+pun.getValoracion());
		punToCsv.add(""+pun.getItem().getNumero());
		punToCsv.add(""+pun.getEvaluacion().getId());
		CSVUtil.writeLine(writer, punToCsv);
	}
	
	protected static void writeEvaluation(Writer writer, Evaluacion eva) throws IOException {
		List<String> evaToCsv = new ArrayList<String>();
		evaToCsv.add(""+eva.getId());
		evaToCsv.add(eva.getFecha().toString());
		evaToCsv.add(eva.getAlumno().getCodigo());
		CSVUtil.writeLine(writer, evaToCsv);
	}

}
