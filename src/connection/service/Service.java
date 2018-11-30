package connection.service;

import java.util.List;

import model.Alumno;
import model.Aula;
import model.Profesor;

public interface Service<T> {
	
	public List<T> getAll();
	
	public boolean edit(long id);
	
	public boolean delete(long id);

}
