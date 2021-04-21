package asboot.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()//

				.withClient("uaa").secret("{noop}s3cr3t").scopes("all", "client", "account", "actuator")// .autoApprove(false)
				.authorizedGrantTypes("client_credentials", "implicit", "password", "authorization_code",
						"refresh_token")
				.resourceIds(ResourceServerConfig.RESOURCE_ID, "res").accessTokenValiditySeconds(30)
				.refreshTokenValiditySeconds(60)
				.redirectUris("http://oauth2.sso/login", "http://oauth2.client:8080/uaa",
						"http://oauth2.server:8822/swagger-ui/oauth2-redirect.html")//

				.and()//

				.withClient("uaa-client").secret("{noop}s3cr3t").scopes("client")// .autoApprove(true)
				.authorizedGrantTypes("client_credentials").resourceIds(ResourceServerConfig.RESOURCE_ID)
				.accessTokenValiditySeconds(30)//

				.and()//

				.withClient("uaa-implicit").secret("{noop}s3cr3t").scopes("account")// .autoApprove(false)
				.authorizedGrantTypes("implicit").resourceIds(ResourceServerConfig.RESOURCE_ID)
				.accessTokenValiditySeconds(30).redirectUris("http://www.baidu.com")//

				.and()//

				.withClient("uaa-password").secret("{noop}s3cr3t").scopes("account")// .autoApprove(true)
				.authorizedGrantTypes("password", "refresh_token").resourceIds(ResourceServerConfig.RESOURCE_ID)
				.accessTokenValiditySeconds(30).refreshTokenValiditySeconds(60)//

				.and()//

				.withClient("uaa-code").secret("{noop}s3cr3t").scopes("account")// .autoApprove(false)
				.authorizedGrantTypes("authorization_code", "refresh_token")
				.resourceIds(ResourceServerConfig.RESOURCE_ID).accessTokenValiditySeconds(30)
				.refreshTokenValiditySeconds(60)
				.redirectUris("http://oauth2.sso/login", "http://oauth2.client:8080/uaa")//
		;
// GET http://oauth2.server:8822/oauth/authorize?response_type=token&client_id=uaa-implicit&redirect_uri=http://www.baidu.com&state=123456

	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://oauth2.resource:8081");
		config.addAllowedHeader("authorization");
		config.addAllowedMethod("POST");
//		config.setAllowCredentials(true);
//		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/oauth/token", config);

		security.checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients()
				.addTokenEndpointAuthenticationFilter(new CorsFilter(configSource));
//		https://stackoverflow.com/questions/49424426/filterregistrationbean-necessary-to-enable-cors-support-with-spring-security?r=SearchResults
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.authenticationManager(this.authenticationManager).userDetailsService(this.userDetailsService)
				.tokenStore(new RedisTokenStore(this.redisConnectionFactory));

	}

}
