package asboot.auth.ctrl;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@RequestMapping({ "/", "/index" })
	public String index() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOG.info("authentication:{}", authentication);
		return "index";
	}

	@RequestMapping("/uaa")
	public String uaa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OAuth2AccessToken accessToken = this.oAuth2RestTemplate.getAccessToken();
		LOG.info("accessToken:{}", accessToken);

		SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		if (null != savedRequest) {
			String redirectUrl = savedRequest.getRedirectUrl();
			LOG.info("redirectUrl:{}", redirectUrl);

			request.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
			response.sendRedirect(redirectUrl);
		}
		return "uaa";
	}

	public void abc(String name, String value, Type... types) {

	}

}
