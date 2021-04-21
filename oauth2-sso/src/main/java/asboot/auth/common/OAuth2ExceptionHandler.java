package asboot.auth.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class OAuth2ExceptionHandler extends AccessDeniedHandlerImpl {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (accessDeniedException.getCause() instanceof OAuth2Exception) {
			OAuth2Exception cause = (OAuth2Exception) accessDeniedException.getCause();
			response.sendError(cause.getHttpErrorCode(), cause.getSummary());
		} else {
			super.handle(request, response, accessDeniedException);
		}
	}

}
