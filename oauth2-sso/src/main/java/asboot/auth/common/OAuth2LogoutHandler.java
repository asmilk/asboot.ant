package asboot.auth.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LogoutHandler implements LogoutHandler {

	private static final Logger LOG = LoggerFactory.getLogger(OAuth2LogoutHandler.class);

	@Value("${asboot.security.oauth2.server.revoke-token-uri}")
	private String revokeTokenUrl;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		LOG.info("#logout");
		LOG.info("authentication:{}", authentication);
		try {
			this.oAuth2RestTemplate.getAccessToken();
			boolean revokeToken = this.oAuth2RestTemplate.getForObject(this.revokeTokenUrl, Boolean.class);
			LOG.info("revokeToken:{}", revokeToken);
		} catch (UserRedirectRequiredException e) {
			LOG.info("Refrush token expired, no need to revoke access token");
		}
	}

}
