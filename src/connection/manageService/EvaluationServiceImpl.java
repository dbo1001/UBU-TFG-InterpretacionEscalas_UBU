package connection.manageService;

import java.util.List;

import javax.persistence.EntityManager;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Aula;
import model.Evaluacion;
import model.Profesor;
import model.Puntuacion;

public class EvaluationServiceImpl extends ServiceImpl implements ManageService<Evaluacion> {

	@Override
	public List<Evaluacion> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Evaluacion eva) throws ConnectionException {
		if (this.checkFields(eva)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				for(Puntuacion pun: eva.getPuntuacions()) {
					em.persist(pun);
				}
				em.persist(eva);
				em.getTransaction().commit();
			} catch (Exception ex) {

				em.getTransaction().rollback();
				throw ex;
			} finally {
				if (em.isOpen()) {
					em.close();
				}
			}
		}

		return true;
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

	private boolean checkFields(Evaluacion eva) throws ConnectionException {

		if (eva.getPuntuacions() == null || eva.getPuntuacions().size() == 0) {
			throw new ConnectionException(ConnectionError.THERE_IS_NO_PUNTUATIONS);
		}

		return true;
	}

}
