package asboot.auth.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import asboot.auth.common.FormLoginFilter;
import asboot.auth.common.OAuth2LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String REMEMBER_ME_KEY = "asbootOAuth2Server";

	@Value("${security.oauth2.client.access-token-uri}")
	private String accessTokenUri;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private OAuth2LogoutSuccessHandler oauth2LogoutSuccessHandler;

	@Autowired
	private MessageSource messageSource;

	@Resource
	private DataSource dataSource;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("admin").password("{noop}123456").roles("ADMIN").build());
//		manager.createUser(User.withUsername("staff").password("{noop}123456").roles("STAFF").build());
//		manager.createUser(User.withUsername("user").password("{noop}123456").roles("USER").build());
//		manager.createUser(User.withUsername("guest").password("{noop}123456").roles("GUEST").build());
//		manager.createUser(User.withUsername("hacker").password("{noop}123456").roles("HACKER").disabled(true).build());
//		manager.createUser(
//				User.withUsername("graduate").password("{noop}123456").roles("GRADUATE").accountLocked(true).build());
//		manager.createUser(
//				User.withUsername("former").password("{noop}123456").roles("FORMER").accountExpired(true).build());
//		manager.createUser(User.withUsername("repeater").password("{noop}123456").roles("REPEATER")
//				.credentialsExpired(true).build());

		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(this.dataSource);
		manager.setEnableAuthorities(false);
		manager.setEnableGroups(true);
		return manager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(
				WebSecurityConfig.REMEMBER_ME_KEY, this.userDetailsService);
		rememberMeServices.setTokenValiditySeconds(120);

		FormLoginFilter formLoginFilter = new FormLoginFilter();
		formLoginFilter.setAuthenticationManager(super.authenticationManagerBean());
		formLoginFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login"));
		formLoginFilter.setRememberMeServices(rememberMeServices);
		formLoginFilter.setMessageSource(this.messageSource);

		http//
				.authorizeRequests()//
				.antMatchers("/", "/error").permitAll()//
				.anyRequest().authenticated().and()//
				.addFilterBefore(formLoginFilter, UsernamePasswordAuthenticationFilter.class)//
				.formLogin().loginPage("/login").permitAll().and()//
				.csrf().ignoringRequestMatchers(new AntPathRequestMatcher(this.accessTokenUri, "POST")).and()//
				.rememberMe().rememberMeServices(rememberMeServices).and()//
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(this.oauth2LogoutSuccessHandler);
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/favicon.ico", "/captcha", "/script/**", "/style/**", "/upload/**", //
				// "/swagger-ui.html**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**",
				// "/csrf"
				"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new RememberMeAuthenticationProvider(WebSecurityConfig.REMEMBER_ME_KEY))
				.userDetailsService(this.userDetailsService)// .passwordEncoder(this.passwordEncoder)
		;
	}

}
