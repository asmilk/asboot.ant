package asboot.auth.domain;

import java.io.Serializable;

public class Details implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2422228553258614307L;

	private String remoteAddress;

	private String sessionId;

	private String tokenValue;

	private String tokenType;

	private String decodedDetails;

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getDecodedDetails() {
		return decodedDetails;
	}

	public void setDecodedDetails(String decodedDetails) {
		this.decodedDetails = decodedDetails;
	}

	@Override
	public String toString() {
		return "Details [remoteAddress=" + remoteAddress + ", sessionId=" + sessionId + ", tokenValue=" + tokenValue
				+ ", tokenType=" + tokenType + ", decodedDetails=" + decodedDetails + "]";
	}

}
