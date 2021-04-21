package asboot.auth.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceAdvice.class);

	@ExceptionHandler(OAuth2Exception.class)
	public void handleOAuth2Exception(OAuth2Exception e, HttpServletResponse response) throws IOException {
		LOG.info("{}:{}", e.getClass().getName(), e.getSummary());
		response.sendError(e.getHttpErrorCode(), e.getSummary());
	}

}
