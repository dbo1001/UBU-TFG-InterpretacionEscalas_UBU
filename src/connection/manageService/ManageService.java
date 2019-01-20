package connection.manageService;

import java.util.List;

import connection.ConnectionException;

public interface ManageService<T, O> {
	
	public List<T> getAll();
	
	public T getOne(O object);
	
	public boolean add(T object) throws ConnectionException;
	
	public boolean edit(T object) throws ConnectionException;
	
	public boolean delete(T object) throws ConnectionException;

}
