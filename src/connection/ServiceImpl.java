package connection;

import java.util.regex.Pattern;

import javax.persistence.EntityManager;

public abstract class ServiceImpl {

	private EntityManager em;
	protected final Pattern namePattern = Pattern.compile("[^a-zÁáÉéÍíÓóÚúÀàÈèÌìÒòÙùÄäËëÏïÖöÜüÂâÊêÎîÔôÛûÑñÇç ]", Pattern.CASE_INSENSITIVE);
	protected final Pattern specialCharacterPattern = Pattern.compile("[^a-z0-9ÁáÉéÍíÓóÚúÀàÈèÌìÒòÙùÄäËëÏïÖöÜüÂâÊêÎîÔôÛûÑñÇç ]", Pattern.CASE_INSENSITIVE);
	protected final Pattern noCapsLetterPattern = Pattern.compile("[^A-Z]");
	protected final Pattern noNumberPattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
	protected final Pattern nifPattern = Pattern.compile("[^A-Z0-9]");
	protected final Pattern passPattern = Pattern.compile("[,;.:\"\']");
	
	protected EntityManager getEntityManager() {
		return EntityManagerGenerator.getNewEntityManager();
	}
}
