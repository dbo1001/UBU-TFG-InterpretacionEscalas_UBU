package service;

public interface StudentService extends Service{
	
	public boolean editStudent(long id);
	
	public boolean deleteStudent(long id);
}
