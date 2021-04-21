package asboot.auth.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import asboot.auth.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping(path = "/login", params = "!redirect_uri")
	public String login(@ModelAttribute("user") UserEntity user,
			@SessionAttribute(name = "SPRING_SECURITY_SAVED_REQUEST", required = false) DefaultSavedRequest savedRequest) {
		log.info("savedRequest:{}", savedRequest);

		String result = "login";
		String query;
		if (null != savedRequest && null != (query = savedRequest.getQueryString())) {
			if (query.contains("http://oauth2.sso/login")) {
				result = "login/oauth2-sso";
			} else if (query.contains("http://oauth2.client:8080/uaa")) {
				result = "login/oauth2-client";
			}
		}
		return result;
	}

	@GetMapping(path = "/login", params = "redirect_uri")
	public void loginError(HttpServletResponse response, @RequestParam("redirect_uri") String redirectUri)
			throws IOException {
		log.info("redirect_uri:{}", redirectUri);
		response.sendRedirect(redirectUri);
	}

}
