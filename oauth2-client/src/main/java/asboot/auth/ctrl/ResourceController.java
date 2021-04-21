package asboot.auth.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uaa")
public class ResourceController {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	@Value("${security.oauth2.resource.user-info-uri}")
	private String userInfoUrl;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@RequestMapping("/user_info")
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userInfo = null;
		try {
			OAuth2AccessToken accessToken = this.oAuth2RestTemplate.getAccessToken();
			LOG.info("accessToken:{}", accessToken);
			userInfo = this.oAuth2RestTemplate.getForObject(this.userInfoUrl, String.class);
			LOG.info("userInfo:{}", userInfo);
		} catch (UserRedirectRequiredException e) {
			SavedRequest savedRequest = new SimpleSavedRequest(request.getServletPath());
			request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
			response.sendRedirect("/uaa");
		}
		return userInfo;
	}

}
