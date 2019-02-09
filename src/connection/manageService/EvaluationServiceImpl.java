package connection.manageService;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import connection.ConnectionError;
import connection.ConnectionException;
import connection.ServiceImpl;
import model.Evaluacion;
import model.Puntuacion;

public class EvaluationServiceImpl extends ServiceImpl implements ManageService<Evaluacion, Timestamp> {

	@Override
	public List<Evaluacion> getAll() {
		EntityManager em = super.getEntityManager();
		List<Evaluacion> result = em.createNamedQuery("Evaluacion.findAll", Evaluacion.class).getResultList();
		if (em.isOpen()) {
			em.close();
		}
		return result;
	}

	@Override
	public Evaluacion getOneById(long id) {
		EntityManager em = super.getEntityManager();
		Evaluacion result;
		try {
			result = em.createNamedQuery("Evaluacion.findById", Evaluacion.class).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException nrE) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public Evaluacion getOne(Timestamp fecha) {
		EntityManager em = super.getEntityManager();
		Evaluacion result;
		try {
			result = em.createNamedQuery("Evaluacion.findByFecha", Evaluacion.class).setParameter("fecha", fecha)
					.getSingleResult();
		} catch (NoResultException nrE) {
			return null;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
		return result;
	}

	@Override
	public boolean add(Evaluacion eva) throws ConnectionException {
		if (this.checkFields(eva)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				for (Puntuacion pun : eva.getPuntuacions()) {
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
	public boolean edit(Evaluacion eva) throws ConnectionException {
		if (this.checkFields(eva)) {

			EntityManager em = this.getEntityManager();

			try {
				em.getTransaction().begin();
				Evaluacion oldEva = em.createNamedQuery("Evaluacion.findById", Evaluacion.class)
						.setParameter("id", eva.getId()).getSingleResult();
				for (Puntuacion pun : oldEva.getPuntuacions()) {
					if (!eva.getPuntuacions().contains(pun)) {
						em.remove(em.contains(pun) ? pun : em.merge(pun));
					}
				}
				for (Puntuacion pun : eva.getPuntuacions()) {
					if (oldEva.getPuntuacions().contains(pun)) {
						//TODO borrar
						//System.out.println(oldEva.getPuntuacions().get(oldEva.getPuntuacions().indexOf(pun)).getId() +"/"+pun.getId());
						//System.out.println("Item: "+pun.getItem().getNumero()+" Eva: "+pun.getEvaluacion().getId());
						em.merge(pun);
					} else {
						em.persist(pun);
					}
				}
				em.merge(eva);
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
	public boolean delete(Evaluacion eva) {

		EntityManager em = this.getEntityManager();

		try {
			em.getTransaction().begin();
			for (Puntuacion pun : eva.getPuntuacions()) {
				em.remove(em.contains(pun) ? pun : em.merge(pun));
			}
			em.remove(em.contains(eva) ? eva : em.merge(eva));
			em.getTransaction().commit();
		} catch (Exception ex) {

			em.getTransaction().rollback();
			throw ex;
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}

		return true;
	}

	private boolean checkFields(Evaluacion eva) throws ConnectionException {

		if (eva.getPuntuacions() == null || eva.getPuntuacions().size() == 0) {
			throw new ConnectionException(ConnectionError.THERE_IS_NO_PUNTUATIONS);
		}

		return true;
	}

}
