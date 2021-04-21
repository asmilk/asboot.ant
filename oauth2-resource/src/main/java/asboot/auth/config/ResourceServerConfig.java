package asboot.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	public static final String RESOURCE_ID = "res";

	@Autowired
	private RoleHierarchy roleHierarchy;

	@Autowired
	private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

	@Bean
	public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler() {
		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(this.roleHierarchy);
//		expressionHandler.setExpressionParser(new SpelExpressionParser());
		return expressionHandler;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().expressionHandler(this.oAuth2WebSecurityExpressionHandler)
				.requestMatchers(EndpointRequest.toAnyEndpoint())
				.access("hasRole('ADMIN') and #oauth2.hasAnyScope('all','actuator')")//
				.anyRequest().authenticated();

//		http.authorizeRequests()
//        .antMatchers(
//        		"/swagger-ui.html**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**")
//        .permitAll();
	}

}
