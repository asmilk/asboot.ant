package asboot.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import asboot.auth.common.OAuth2ExceptionHandler;
import asboot.auth.common.OAuth2LogoutHandler;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private OAuth2LogoutHandler oAuth2LogoutHandler;

	@Autowired
	private RoleHierarchy roleHierarchy;

	@Autowired
	private OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

	@Value("${asboot.security.oauth2.server.logout-uri}")
	private String logoutUrl;

	@Bean
	public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler() {
		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(this.roleHierarchy);
//		expressionHandler.setExpressionParser(new SpelExpressionParser());
		return expressionHandler;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.expressionHandler(this.oAuth2WebSecurityExpressionHandler)//
				.ignoring().mvcMatchers("/image/**", "/favicon.ico");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.getSharedObjects().forEach((key, value) -> {
			LOG.info("{}:{}", key, value);
		});

		http.authorizeRequests().expressionHandler(this.oAuth2WebSecurityExpressionHandler)//
				.antMatchers("/", "/error").permitAll()//
				.antMatchers("/scope/all").access("#oauth2.hasScope('all')")//
				.antMatchers("/scope/client").access("#oauth2.hasAnyScope('all','client','account')")//
				.antMatchers("/scope/account").access("#oauth2.hasAnyScope('all','account')")//
				.antMatchers("/role/user").hasRole("USER")//
				.antMatchers("/role/admin").hasRole("ADMIN")//
				.anyRequest().authenticated().and()//
				.exceptionHandling().accessDeniedHandler(new OAuth2ExceptionHandler()).and()//
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.addLogoutHandler(this.oAuth2LogoutHandler).logoutSuccessUrl(this.logoutUrl);
	}

}
