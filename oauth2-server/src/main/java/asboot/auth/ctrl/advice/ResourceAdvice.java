package asboot.auth.ctrl.advice;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ResourceAdvice {

	@ExceptionHandler(ValidationException.class)
	public void handleValidationException(ValidationException e, HttpServletResponse response) throws IOException {
		log.info("message:{}", e.getMessage());
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

}
