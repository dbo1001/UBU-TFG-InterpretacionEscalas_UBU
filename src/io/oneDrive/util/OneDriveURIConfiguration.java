package io.oneDrive.util;

/**
 * Configuration with templates for URIs in OneDrive API.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
public class OneDriveURIConfiguration {
	
	/** Root URI for web services. */
	public static final String URI_ROOT = "https://graph.microsoft.com/v1.0/me/drive/root";	
	
	/** URI for testing access token. */
	public static final String URI_TEST = "https://graph.microsoft.com/v1.0/me/drive";	

	/**
	 * Renew access token. when the access token is incorrect or has expired, we
	 * use this token to obtain a new valid access token. It is mandatory to
	 * configure de app with the scope of offline access.
	 */
	public static final String URI_RENEW_TOKEN = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
}
