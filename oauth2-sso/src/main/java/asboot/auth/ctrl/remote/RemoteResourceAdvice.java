package asboot.auth.ctrl.remote;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("asboot.auth.ctrl.remote")
public class RemoteResourceAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(RemoteResourceAdvice.class);

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@ModelAttribute
	public void verifyRefreshToken() {
		OAuth2AccessToken accessToken = this.oAuth2RestTemplate.getAccessToken();
		LOG.info("accessToken:{}", accessToken);
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		LOG.info("refreshToken:{}", refreshToken);
	}

	@ExceptionHandler(UserRedirectRequiredException.class)
	public void handleRefreshTokenExpired(UserRedirectRequiredException e, HttpServletResponse response)
			throws IOException {
		LOG.info("Refrush token expired, redirect to logout");
		response.sendRedirect("/logout");
	}

}
