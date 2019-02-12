package connection;

import java.util.regex.Pattern;

import javax.persistence.EntityManager;
/**
 * Superclase para las clases que implementan las transacciones
 * @author Mario Núñez Izquierdo
 *
 */
public abstract class ServiceImpl {

	@SuppressWarnings("unused")
	private EntityManager em;
	protected final Pattern namePattern = Pattern.compile("[^a-zÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜáéíóúàèìòùâêîôûäëïöüÑñ ]", Pattern.CASE_INSENSITIVE);
	protected final Pattern specialCharacterPattern = Pattern.compile("[^a-z0-9ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜáéíóúàèìòùâêîôûäëïöüÑñ ]", Pattern.CASE_INSENSITIVE);
	protected final Pattern noCapsLetterPattern = Pattern.compile("[^A-Z]");
	protected final Pattern noNumberPattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
	protected final Pattern nifPattern = Pattern.compile("[^A-Z0-9]");
	
	/**
	 * Método para obtener entityManagers de la clase EntityManagerGenerator
	 * @return nuevo entityManager
	 */
	protected EntityManager getEntityManager() {
		return EntityManagerGenerator.getNewEntityManager();
	}
}
