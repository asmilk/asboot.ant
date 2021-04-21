package asboot.auth.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import com.google.code.kaptcha.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";
	public static final String SPRING_SECURITY_FORM_REDIRECT_URL_KEY = "redirect_url";

	private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
	private String redirectUrlParameter = SPRING_SECURITY_FORM_REDIRECT_URL_KEY;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String captcha = this.obtainCaptcha(request);
		log.info("{}:{}", this.captchaParameter, captcha);

		String kaptchaText = (String) WebUtils.getSessionAttribute(request, Constants.KAPTCHA_SESSION_KEY);
//		long kaptchaDate = (long) WebUtils.getSessionAttribute(request, Constants.KAPTCHA_SESSION_DATE);
		log.info("{}:{}", Constants.KAPTCHA_SESSION_KEY, kaptchaText);

		if (!kaptchaText.equalsIgnoreCase(captcha)) {
			throw new WrongCaptchaException(this.messages.getMessage("FormLoginFilter.wrongCaptcha", "Wrong Captcha"));
		}

		String redirectUrl = this.obtainRedirectUrl(request);
		log.info("redirect_url:{}", redirectUrl);

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(redirectUrl).build();
		DefaultSavedRequest savedRequest = new DefaultSavedRequest.Builder().setScheme(uriComponents.getScheme())
				.setServerName(uriComponents.getHost()).setServerPort(uriComponents.getPort())
				.setRequestURI(uriComponents.getPath()).setQueryString(uriComponents.getQuery()).build();
		log.info("redirect_url:{}", savedRequest.getRedirectUrl());
		WebUtils.setSessionAttribute(request, "SPRING_SECURITY_SAVED_REQUEST", savedRequest);

		return super.attemptAuthentication(request, response);
	}

	protected String obtainCaptcha(HttpServletRequest request) {
		return request.getParameter(this.captchaParameter);
	}

	protected String obtainRedirectUrl(HttpServletRequest request) {
		return request.getParameter(this.redirectUrlParameter);
	}

}
