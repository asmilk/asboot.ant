package asboot.auth;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class OAuth2SsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2SsoApplication.class, args);
	}

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(resource, context);
		oauth2RestTemplate.setRetryBadAccessTokens(false);
		List<HttpMessageConverter<?>> messageConverters = oauth2RestTemplate.getMessageConverters();
		for(HttpMessageConverter<?> messageConverter : messageConverters) {
			log.info(messageConverter.toString());
		}
		return oauth2RestTemplate;
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF > ROLE_USER" + System.getProperty("line.separator")
				+ "ROLE_A > ROLE_B > ROLE_C");
		return roleHierarchy;
	}

}
