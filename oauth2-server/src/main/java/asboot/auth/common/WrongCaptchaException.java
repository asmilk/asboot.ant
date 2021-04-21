package asboot.auth.common;

import org.springframework.security.core.AuthenticationException;

public class WrongCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = -2026570491888897699L;

	public WrongCaptchaException(String msg) {
		super(msg);
	}

}
