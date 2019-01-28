package io;

import java.io.IOException;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Profesor;

public interface IOControl {

	public boolean exportData();

	public boolean importData();

	public void generateCSV() throws IOException;

}
