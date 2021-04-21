package asboot.auth.ctrl.remote;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import asboot.auth.domain.UserInfo;

@RestController
@RequestMapping("/uaa")
public class UaaResourceController {

	private static final String SERVICE = "http://oauth2.server:8822/uaa";

	@Value("${security.oauth2.resource.user-info-uri}")
	private String userInfoUrl;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@GetMapping("/user_info")
	public UserInfo getUserInfo() {
		return this.oAuth2RestTemplate.getForObject(this.userInfoUrl, UserInfo.class);
	}

	@GetMapping("/role/user")
	public boolean hasRoleUser() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/role/user", Boolean.class);
	}

	@GetMapping("/role/admin")
	public boolean hasRoleAdmin() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/role/admin", Boolean.class);
	}

	@GetMapping("/scope/all")
	public boolean hasScopeAll() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/scope/all", Boolean.class);
	}

	@GetMapping("/scope/client")
	public boolean hasScopeAllOrClient() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/scope/client", Boolean.class);
	}

	@GetMapping("/scope/account")
	public boolean hasScopeAllOrAccount() throws IOException {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/scope/account", Boolean.class);
	}

	@GetMapping("/auth/admin/actuator")
	public String hasAuthAdminActuator() throws IOException {
		return this.oAuth2RestTemplate.getForObject("http://oauth2.server:8822/actuator", String.class);
	}

	@GetMapping("/auth/all/admin")
	public boolean hasAuthAllAdmin() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/auth/all/admin", Boolean.class);
	}

	@GetMapping("/auth/user/client")
	public boolean hasAuthUserClient() {
		return this.oAuth2RestTemplate.getForObject(SERVICE + "/auth/user/client", Boolean.class);
	}

}
