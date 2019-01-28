package io.oneDrive.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import io.oneDrive.response.Content;
import io.oneDrive.response.RenewToken;

/**
 * One Drive API.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
public abstract class OneDriveAPI {

	/** Current access token. */
	private static String ACCESS_TOKEN = null;

	/**
	 * Tests current access token.
	 * 
	 * @param httpClient client
	 * @return true if the current access token is active, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean testCurrentAccessToken(CloseableHttpClient httpClient) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_TEST);
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		boolean state = false;
		try(CloseableHttpResponse response = httpClient.execute(httpget)) {
			String resultado = EntityUtils.toString(response.getEntity());
			if (!resultado.contains("InvalidAuthenticationToken")) {
				state = true;
			}	
		}
		return state;
	}

	/**
	 * List the content of a drive item.
	 * 
	 * @param httpClient client
	 * @param path       path
	 * @return true if the directory has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_list_children?view=odsp-graph-online">One
	 *      Drive API</a>
	 */
	public static Content listDriveItem(CloseableHttpClient httpClient, String path) throws Exception {
		URI uri = null;
		if (path.length() > 0) {
			uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + ":/children");
		} else { // root path
			uri = new URI(OneDriveURIConfiguration.URI_ROOT + "/children");
		}
		System.out.println(httpClient);
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		Content content = null;
		try (CloseableHttpResponse response = httpClient.execute(httpget)) {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				System.err.printf("Código de retorno: %d Drive item NO EXISTE%n", statusLine.getStatusCode());
			} else {
				System.out.printf("Código de retorno: %d Drive item EXISTE%n", statusLine.getStatusCode());
				String json = EntityUtils.toString(response.getEntity(), "UTF-8");
				// Convert from JSON to object
				Gson gson = new com.google.gson.Gson();
				content = gson.fromJson(json, Content.class);
			}
		}
		return content;
	}

	/**
	 * Check if a drive item exists. A drive item can be a folder or file.
	 * 
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_get?view=odsp-graph-online">One
	 *      Drive API</a>
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @return true if the directory has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean checkFile(CloseableHttpClient httpClient, String path, String file) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + file + ":");
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpget)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_OK);
		}
		return state;
	}

	/**
	 * Check status of the http method.
	 * 
	 * @param state      state
	 * @param response   http response
	 * @param httpStatus expected http status
	 * @return true if the status is the expected code, false in other case
	 */
	private static boolean checkHttpStatus(boolean state, CloseableHttpResponse response, int httpStatus) {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() != httpStatus) {
			System.err.printf("ERROR Código de retorno: %d %n", statusLine.getStatusCode());
		} else {
			System.out.printf("OK Código de retorno: %d %n", statusLine.getStatusCode());
			state = true;
		}
		return state;
	}

	/**
	 * Check if a directory exists.
	 * 
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_get?view=odsp-graph-online">One
	 *      Drive API</a>
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @return true if the directory has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean checkDirectory(CloseableHttpClient httpClient, String path) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + ":");
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpget)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_OK);
		}
		return state;
	}

	/**
	 * Deletes the directory.
	 * 
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_delete?view=odsp-graph-online">One
	 *      Drive API</a>
	 * @param httpClient client
	 * @param path       path
	 * @return true if the directory has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean deleteFolder(CloseableHttpClient httpClient, String path) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path);
		HttpDelete httpDelete = new HttpDelete(uri);
		httpDelete.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_NO_CONTENT);
		}
		return state;
	}

	/**
	 * Deletes the file.
	 * 
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_delete?view=odsp-graph-online">One
	 *      Drive API</a>
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean deleteFile(CloseableHttpClient httpClient, String path, String file) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + file + ":");
		HttpDelete httpDelete = new HttpDelete(uri);
		httpDelete.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_NO_CONTENT);
		}
		return state;
	}

	/**
	 * Creates a folder.
	 * 
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_post_children?view=odsp-graph-online">One
	 *      Drive API</a>
	 * @param httpClient client
	 * @param path       path
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 */
	public static boolean createFolder(CloseableHttpClient httpClient, String directoryName) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + "/children");
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		/*
		 * @microsoft.graph.conflictBehavior string The conflict resolution behavior for
		 * actions that create a new item. You can use the values fail, replace, or
		 * rename. The default for PUT is replace. An item will never be returned with
		 * this annotation. Write-only.
		 * 
		 * See: https://docs.microsoft.com/en-us/onedrive/developer/rest-api/resources/
		 * driveitem?view=odsp-graph-online
		 */
		StringEntity requestEntity = new StringEntity(
				"{\"name\":\"" + directoryName
						+ "\",\"folder\": { }, \"@microsoft.graph.conflictBehavior\": \"replace\"}",
				ContentType.APPLICATION_JSON);

		httpPost.setEntity(requestEntity);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_CREATED);
		}
		return state;
	}
	
	/**
	 * Uploads file.
	 * 
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @param mode 		 file mode text or binary
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/concepts/upload?view=odsp-graph-online">One
	 *      Drive API</a>
	 */
	private static boolean uploadFile(CloseableHttpClient httpClient, String path, String file, String mode) throws Exception {
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + file + ":/content");
		HttpPut httpPut = new HttpPut(uri);
		httpPut.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		httpPut.setHeader(HttpHeaders.CONTENT_TYPE,  mode);
		FileEntity reqEntity = new FileEntity(new File(file), ContentType.TEXT_PLAIN);
		httpPut.setEntity(reqEntity);
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
			state = checkHttpStatus(state, response, HttpStatus.SC_CREATED);
		}
		return state;
	}

	/**
	 * Uploads a text file.
	 * 
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/concepts/upload?view=odsp-graph-online">One
	 *      Drive API</a>
	 */
	public static boolean uploadTextFile(CloseableHttpClient httpClient, String path, String file) throws Exception {
		return uploadFile(httpClient, path, file, "text/plain");
	}
	
	/**
	 * Uploads binary file.
	 * 
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/concepts/upload?view=odsp-graph-online">One
	 *      Drive API</a>
	 */
	public static boolean uploadBinaryFile(CloseableHttpClient httpClient, String path, String file) throws Exception {
		return uploadFile(httpClient, path, file, "application/octet-stream");
	}

	/**
	 * Downloads a file.
	 * 
	 * @param httpClient client
	 * @param path       path (without slash at beginning or end)
	 * @param file       file name and extension
	 * @return true if the file has been correctly deleted, false in other case
	 * @throws Exception if there is any error
	 * @see <a href=
	 *      "https://docs.microsoft.com/en-us/onedrive/developer/rest-api/api/driveitem_get_content?view=odsp-graph-online">One
	 *      Drive API</a>
	 */
	public static boolean downloadFile(CloseableHttpClient httpClient, String path, String file) throws Exception {
		// URI uri = new URI(OneDriveURIConfiguration.URI_DOWNLOAD_FILE);
		URI uri = new URI(OneDriveURIConfiguration.URI_ROOT + ":" + path + file + ":/content");
		// = URI_ROOT + ":/Documentos/Prueba.txt:/content";
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("Authorization", "Bearer " + ACCESS_TOKEN);
		httpget.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		boolean state = false;
		try (CloseableHttpResponse response = httpClient.execute(httpget)) {
			writeToDiskDownloadedFile(file, response);
			state = checkHttpStatus(state, response, HttpStatus.SC_OK);
		}
		return state;
	}

	/**
	 * Writes downloaded file in disk.
	 * 
	 * @param file     file
	 * @param response response
	 * @throws IOException           if exists an IO error
	 * @throws FileNotFoundException if exists a file error
	 */
	private static void writeToDiskDownloadedFile(String file, CloseableHttpResponse response)
			throws IOException, FileNotFoundException {
		// Write file in current directory...
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			File myFile = new File(file);
			try (FileOutputStream outstream = new FileOutputStream(myFile)) {
				entity.writeTo(outstream);
			}
		}
	}

	/**
	 * Renew the access token of the application.
	 * 
	 * @throws Exception problem renewing the access token
	 */
	public static void renewAccessToken(CloseableHttpClient httpclient) throws Exception {
		URI uriRenewToken = new URI(OneDriveURIConfiguration.URI_RENEW_TOKEN);
		HttpPost httppostRenewToken = new HttpPost(uriRenewToken);
		httppostRenewToken.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		List<NameValuePair> arguments = configureOneDriveClientParametersForRenewToken();
		httppostRenewToken.setEntity(new UrlEncodedFormEntity(arguments));
		try (CloseableHttpResponse responseRenewToken = httpclient.execute(httppostRenewToken)) {
			RenewToken respuestaJSON = convertRenewResponseJSONToObject(responseRenewToken);
			// Renew access token...
			ACCESS_TOKEN = respuestaJSON.getAccessToken();
		}
	}

	/**
	 * Converts the renewal response in JSON to objetc.
	 * 
	 * @param responseRenewToken response HTTP
	 * @return object containing the new access token
	 * @throws IOException if exists any problem
	 */
	private static RenewToken convertRenewResponseJSONToObject(CloseableHttpResponse responseRenewToken)
			throws IOException {
		String json = EntityUtils.toString(responseRenewToken.getEntity(), "UTF-8");
		// Convert from JSON to object
		Gson gson = new com.google.gson.Gson();
		RenewToken respuestaJSON = gson.fromJson(json, RenewToken.class);
		return respuestaJSON;
	}

	/**
	 * Configure the client parameters for renew access token.
	 * 
	 * @see OneDriveClientConfiguration
	 * @return parameters
	 */
	private static List<NameValuePair> configureOneDriveClientParametersForRenewToken() {
		List<NameValuePair> arguments = new ArrayList<NameValuePair>(4);
		arguments.add(new BasicNameValuePair("client_id", OneDriveClientConfiguration.CLIENT_ID));
		arguments.add(new BasicNameValuePair("redirect_uri", OneDriveClientConfiguration.REDIRECT_URI));
		arguments.add(new BasicNameValuePair("refresh_token", OneDriveClientConfiguration.REFRESH_TOKEN));
		arguments.add(new BasicNameValuePair("grant_type", "refresh_token"));
		return arguments;
	}
}
