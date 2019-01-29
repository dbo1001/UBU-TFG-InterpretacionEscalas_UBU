package io;

import java.io.IOException;
import java.util.List;

import model.Alumno;
import model.Aula;
import model.Profesor;

public interface IOControl {

	public boolean exportData() throws IOException;

	public boolean importData();

}
