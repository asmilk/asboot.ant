package asboot.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@SpringBootApplication
public class OAuth2ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourceApplication.class, args);
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF > ROLE_USER" + System.getProperty("line.separator")
				+ "ROLE_A > ROLE_B > ROLE_C");
		return roleHierarchy;
	}

}
