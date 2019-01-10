package connection.manageService;

import java.util.List;

import connection.ConnectionException;
import connection.ServiceImpl;
import model.Evaluacion;

public class EvaluationServiceImpl extends ServiceImpl implements ManageService<Evaluacion>{

	@Override
	public List<Evaluacion> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean add(Evaluacion object) throws ConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Evaluacion object) throws ConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Evaluacion object) {
		// TODO Auto-generated method stub
		return false;
	}

}
