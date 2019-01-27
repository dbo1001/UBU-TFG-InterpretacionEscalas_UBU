package io.oneDrive.response;

/**
 * Information renewing access token.
 * 
 * @author Raúl Marticorena
 * @since 1.0
 */
public class RenewToken {

	private String token_type;
	private String scope;
	private int expires_in;
	private int ext_expires_in;
	private String access_token;
	private String refresh_token;

	/**
	 * Gets token type.
	 * 
	 * @return the token_type
	 */
	public String getTokeType() {
		return token_type;
	}

	/**
	 * Gets scope.
	 * 
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Gets expires in.
	 * 
	 * @return the expires_in
	 */
	public int getExpiresIn() {
		return expires_in;
	}

	/**
	 * Gest ext expires in.
	 * 
	 * @return the ext_expires_in
	 */
	public int getExtExpiresIn() {
		return ext_expires_in;
	}

	/**
	 * Gets access token.
	 * 
	 * @return the access_token
	 */
	public String getAccessToken() {
		return access_token;
	}

	/**
	 * Gets new refresh token.
	 * 
	 * @return the refresh_token
	 */
	public String getRefreshToken() {
		return refresh_token;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewToken [token_type=").append(token_type).append(", scope=").append(scope)
				.append(", expires_in=").append(expires_in).append(", ext_expires_in=").append(ext_expires_in)
				.append(", access_token=").append(access_token).append(", refresh_token=").append(refresh_token)
				.append("]");
		return builder.toString();
	}
}
