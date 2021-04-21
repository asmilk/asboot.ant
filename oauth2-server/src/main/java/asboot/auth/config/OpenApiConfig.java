package asboot.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(security = { @SecurityRequirement(name = "uaa") })
@SecurityScheme(name = "uaa", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(/*implicit = @OAuthFlow(authorizationUrl = "${security.oauth2.client.user-authorization-uri}", scopes = {
		@OAuthScope(name = "all", description = "scope.all"),
		@OAuthScope(name = "client", description = "scope.client"),
		@OAuthScope(name = "account", description = "scope.account"),
		@OAuthScope(name = "actuator", description = "scope.actuator") }), */password = @OAuthFlow(tokenUrl = "${security.oauth2.client.access-token-uri}", scopes = {
				@OAuthScope(name = "all", description = "scope.all"),
				@OAuthScope(name = "client", description = "scope.client"),
				@OAuthScope(name = "account", description = "scope.account"),
				@OAuthScope(name = "actuator", description = "scope.actuator") }), clientCredentials = @OAuthFlow(tokenUrl = "${security.oauth2.client.access-token-uri}", scopes = {
						@OAuthScope(name = "all", description = "scope.all"),
						@OAuthScope(name = "client", description = "scope.client"),
						@OAuthScope(name = "account", description = "scope.account"),
						@OAuthScope(name = "actuator", description = "scope.actuator") })/*, authorizationCode = @OAuthFlow(authorizationUrl = "${security.oauth2.client.user-authorization-uri}", tokenUrl = "${security.oauth2.client.access-token-uri}", scopes = {
								@OAuthScope(name = "all", description = "scope.all"),
								@OAuthScope(name = "client", description = "scope.client"),
								@OAuthScope(name = "account", description = "scope.account"),
								@OAuthScope(name = "actuator", description = "scope.actuator") })*/))
public class OpenApiConfig {

}
