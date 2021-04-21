package asboot.auth.config;
/*
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

//https://github.com/springfox/springfox/issues/2390
//@Configuration
//@EnableSwagger2
//@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {/*

	@Value("${spring.application.name}")
	private String appName;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Value("${security.oauth2.client.scope}")
	private String[] scopes;

	@Value("${security.oauth2.client.user-authorization-uri}")
	private String userAuthorizationUri;

	@Value("${security.oauth2.client.access-token-uri}")
	private String accessTokenUri;

	@Bean
	public Docket docket() {

		List<AuthorizationScope> scopeList = new ArrayList<AuthorizationScope>();
		for (String scope : this.scopes) {
			scopeList.add(new AuthorizationScopeBuilder().scope(scope).description("scope." + scope).build());
		}

		AuthorizationScope[] scopeArray = new AuthorizationScope[scopeList.size()];
		scopeArray = scopeList.toArray(scopeArray);

		SecurityReference securityReference = SecurityReference.builder().reference(this.clientId).scopes(scopeArray)
				.build();

		SecurityContext securityContext = SecurityContext.builder().securityReferences(Arrays.asList(securityReference))
				.forPaths(PathSelectors.regex("/uaa.*|/actuator")).build();

//		TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpointBuilder().url(this.userAuthorizationUri)
//				.build();
//		TokenEndpoint tokenEndpoint = new TokenEndpointBuilder().url(this.accessTokenUri).build();
//
//		AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrantBuilder()
//				.tokenRequestEndpoint(tokenRequestEndpoint).tokenEndpoint(tokenEndpoint).build();

		ResourceOwnerPasswordCredentialsGrant passwordGrant = new ResourceOwnerPasswordCredentialsGrant(
				this.accessTokenUri);

		SecurityScheme securityScheme = new OAuthBuilder().name(this.clientId).grantTypes(Arrays.asList(passwordGrant))
				.scopes(scopeList).build();

		return new Docket(DocumentationType.SWAGGER_2)//
				.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex("/captcha|/uaa.*|/actuator"))
				.build()//
				.ignoredParameterTypes(Principal.class).securityContexts(Arrays.asList(securityContext))
				.securitySchemes(Arrays.asList(securityScheme));
	}

	@Bean
	public SecurityConfiguration securityConfiguration() {
		return SecurityConfigurationBuilder.builder().appName(this.appName).clientId(this.clientId)
				.clientSecret(this.clientSecret)// .scopeSeparator(" ")
				.useBasicAuthenticationWithAccessCodeGrant(true).build();
	}

	@Bean
	public UiConfiguration uiConfiguration() {
		return UiConfigurationBuilder.builder().build();
	}*/

}
