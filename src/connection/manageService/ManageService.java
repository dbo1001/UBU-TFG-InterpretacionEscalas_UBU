package connection.manageService;

import java.util.List;

import model.Alumno;
import model.Aula;
import model.Profesor;

public interface ManageService<T> {
	
	public List<T> getAll();
	
	public boolean edit(long id);
	
	public boolean delete(long id);

}
