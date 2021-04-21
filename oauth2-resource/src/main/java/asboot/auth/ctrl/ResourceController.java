package asboot.auth.ctrl;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	@GetMapping("/user_info")
	public Principal getUserInfo(Principal principal) {
		LOG.info("principal : {}", principal);
		return principal;
	}

	@GetMapping("/role/user")
	@PreAuthorize("hasRole('USER')")
	public boolean hasRoleUser() {
		return true;
	}

	@GetMapping("/role/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean hasRoleAdmin() {
		return true;
	}

	@GetMapping("/scope/all")
	@PreAuthorize("#oauth2.hasScope('all')")
	public boolean hasScopeAll() {
		return true;
	}

	@GetMapping("/scope/client")
	@PreAuthorize("#oauth2.hasAnyScope('all','client','account')")
	public boolean hasScopeAllOrClient() {
		return true;
	}

	@GetMapping("/scope/account")
	@PreAuthorize("#oauth2.hasAnyScope('all','account')")
	public boolean hasScopeAllOrAccount() {
		return true;
	}

	@GetMapping("/auth/all/admin")
	@PreAuthorize("#oauth2.hasScope('all') and hasRole('ADMIN')")
	public boolean hasAuthAllAdmin() {
		return true;
	}

	@GetMapping("/auth/user/client")
	@PreAuthorize("hasRole('USER') and #oauth2.hasAnyScope('all','client','account')")
	public boolean hasAuthUserClient() {
		return true;
	}

}
