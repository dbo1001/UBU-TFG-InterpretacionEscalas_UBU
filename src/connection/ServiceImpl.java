package connection;

import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import connection.manageService.ManageService;

public abstract class ServiceImpl {

	private EntityManager em;
	protected final Pattern namePattern = Pattern.compile("[^a-zÁáÉéÍíÓóÚúÀàÈèÌìÒòÙùÄäËëÏïÖöÜüÂâÊêÎîÔôÛûÑñÇç ]", Pattern.CASE_INSENSITIVE);
	//protected final Pattern nifPattern = Pattern.compile("[^A-Z0-9]");
	
	protected EntityManager getEntityManager() {
		return EntityManagerGenerator.getNewEntityManager();
	}
}
