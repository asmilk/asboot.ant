package asboot.auth.domain;

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7786674229984018779L;
	
	private Details details;

	private Boolean authenticated;

	private Boolean clientOnly;

	private String credentials;

	private String name;

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public Boolean getClientOnly() {
		return clientOnly;
	}

	public void setClientOnly(Boolean clientOnly) {
		this.clientOnly = clientOnly;
	}

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserInfo [details=" + details + ", authenticated=" + authenticated + ", clientOnly=" + clientOnly
				+ ", credentials=" + credentials + ", name=" + name + "]";
	}

}
