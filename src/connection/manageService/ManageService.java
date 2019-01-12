package connection.manageService;

import java.util.List;

import connection.ConnectionException;

public interface ManageService<T> {
	
	public List<T> getAll();
	
	public boolean add(T object) throws ConnectionException;
	
	public boolean edit(T object) throws ConnectionException;
	
	public boolean delete(T object) throws ConnectionException;

}
