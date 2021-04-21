package asboot.auth.ctrl;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class OAuth2ApprovalController {

	@GetMapping("/oauth/confirm_access")
	public String confirmAccess(AuthorizationRequest authorizationRequest) {
		log.info("authorizationRequest:{}", authorizationRequest);
		String clientId = authorizationRequest.getClientId();
		log.info("clientId:{}", clientId);
		return "oauth-approval";
	}

}
