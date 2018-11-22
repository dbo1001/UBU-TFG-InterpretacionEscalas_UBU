package service;

public class StudentServiceImpl extends ServiceImpl implements StudentService {
	
	@Override
	public boolean editStudent(long id) {
		System.err.println("Transaccion no implementada. Alumno: " + id);
		return false;
	}

	@Override
	public boolean deleteStudent(long id) {
		System.err.println("Transaccion no implementada. Alumno: " + id);
		return false;
	}

}
