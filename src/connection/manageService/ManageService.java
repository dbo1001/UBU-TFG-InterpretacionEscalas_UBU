package connection.manageService;

import java.util.List;

import connection.ConnectionException;
import model.Alumno;
import model.Aula;
import model.Profesor;

public interface ManageService<T> {
	
	public List<T> getAll();
	
	public boolean add(T object) throws ConnectionException;
	
	public boolean edit(T object) throws ConnectionException;
	
	public boolean delete(long id);

}
