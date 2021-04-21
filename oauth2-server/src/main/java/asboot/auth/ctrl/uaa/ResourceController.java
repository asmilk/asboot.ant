package asboot.auth.ctrl.uaa;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.chrono.JapaneseDate;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import asboot.auth.entity.AuthorityEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@PropertySource("classpath:open_api.properties")
@RequestMapping(path = "/uaa", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
public class ResourceController {

	@Autowired
	ConsumerTokenServices consumerTokenServices;

	@Autowired
	ObjectMapper mapper;

	@GetMapping("/revoke_token")
	public boolean revoke(Principal principal) {
		boolean result = false;
		if (principal instanceof OAuth2Authentication) {
			OAuth2Authentication authentication = (OAuth2Authentication) principal;
			Object details = authentication.getDetails();
			if (details instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) details;
				String tokenValue = authenticationDetails.getTokenValue();
				log.info("tokenValue:{}", tokenValue);
				result = this.consumerTokenServices.revokeToken(tokenValue);
			}
		}
		return result;
	}

	@GetMapping("/user_info")
	@Operation(summary = "${uaa.user-info.operation.summary}", description = "${uaa.user-info.operation.description}")
	public Principal getUserInfo(Principal principal) {
		log.info("principal : {}", principal);
		return principal;
	}

	@GetMapping("/user_details")
	public UserDetails getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
		log.info("userDetails : {}", userDetails);
		return userDetails;
	}

	@PostMapping("/echo/{path}")
	@Operation(summary = "${uaa.echo.operation.summary}", description = "${uaa.echo.operation.description}")
	public String echo(
			@Parameter(description = "${uaa.echo.parameter.header.description}") @RequestHeader(defaultValue = "DEFAULT_HEADER") String header,
			@Parameter(description = "${uaa.echo.parameter.query.description}") @RequestParam(defaultValue = "DEFAULT_QUERY") String query,
			@Parameter(description = "${uaa.echo.parameter.path.description}") @PathVariable String path,
			@Parameter(description = "${uaa.echo.parameter.body.description}") @RequestBody String body) {
		return String.format("header:%s, query:%s, paht: %s, body:%s", header, query, path, body);
	}
	
	@PostMapping("/valid")
	public String valid(@RequestParam @Min(5) @Max(10) Integer age,
			@RequestParam(required=false) @Parameter(schema = @Schema(type = "string", format = "email")) @Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$") String email,
			@RequestParam @Size(min = 2, max = 5) String address,
			@RequestParam @Parameter(example = "2000-10-31") @DateTimeFormat(iso = ISO.DATE) LocalDate date,
			@RequestParam @Parameter(example = "01:30:00.000-05:00", schema = @Schema(type = "string", format = "time")) @DateTimeFormat(iso = ISO.TIME) OffsetTime time,
			@RequestParam @Parameter(example = "2000-10-31T01:30:00.000-05:00") @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime dateTime) {
		log.info(JapaneseDate.from(dateTime).toString());
		return String.format("age:%d, email:%s, address:%s, date:%tF, time:%tT.%tL%tZ, dateTime:%tc", age, email,
				address, date, time, time, time, dateTime);
	}

	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void upload(HttpServletResponse response, String foo, @RequestPart MultipartFile file)
			throws IllegalStateException, IOException {
		log.info("foo:{}", foo);
		log.info("contentType:{}", file.getContentType());
		log.info("name:{}", file.getName());
		log.info("originalFilename:{}", file.getOriginalFilename());

		String contentType = file.getContentType();
		switch (contentType) {
		case "image/png": {
			response.setContentType(contentType);
			ImageIO.write(ImageIO.read(file.getInputStream()), "png", response.getOutputStream());
			break;
		}
		default: {
			String url = "/upload/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
			log.info("url:{}", url);
			File dest = new File("src/main/resources/static" + url);
			log.info("dest:{}", dest.getAbsolutePath());
			file.transferTo(new File(dest.getAbsolutePath()));
			response.getOutputStream().print(url);
		}
		}
	}
/*
	// @Operation(operationId = "inputAuthority")
	@PostMapping(path = "/authority", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(tags = {"Authority"})
	public AuthorityEntity postAuthority(@Valid @RequestBody AuthorityParam source)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("source:{}", source);
		String content = this.mapper.writeValueAsString(source);
		log.info("content:{}", content);
		AuthorityEntity target = this.mapper.readValue(content, AuthorityEntity.class);
		target.setId(null == target.getId() ? new Random().nextLong() : target.getId());
		log.info("target:{}", target);
		return target;
	}*/

//	@PutMapping("/authority")
//	@ResponseStatus(HttpStatus.CREATED)
//	@Operation(tags = {"Authority"})
//	public AuthorityEntity putAuthority(@ParameterObject @Valid AuthorityParam source)
//			throws JsonParseException, JsonMappingException, IOException {
//		log.info("source:{}", source);
//		source.setId(null);
//		String content = this.mapper.writeValueAsString(source);
//		log.info("content:{}", content);
//		AuthorityEntity target = this.mapper.readValue(content, AuthorityEntity.class);
//		target.setId(new Random().nextLong());
//		log.info("target:{}", target);
//		return target;
//	}
	/*
	@PutMapping("/authority")
	@Operation(tags = {"Authority"})
	@ResponseStatus(HttpStatus.CREATED)
	public AuthorityEntity putAuthority(@ParameterObject AuthorityEntity authority) {

		return null;
	}*/

//	@PatchMapping("/authority")
//	@Parameters({
//			@Parameter(in = ParameterIn.QUERY, required = true, name = "id", schema = @Schema(type = "integer", format = "int64")),
//			@Parameter(in = ParameterIn.QUERY, required = true, name = "authority", schema = @Schema(type = "string", maxLength = 10)),
//			@Parameter(in = ParameterIn.QUERY, required = false, name = "user.username", schema = @Schema(type = "string", pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")),
//			@Parameter(in = ParameterIn.QUERY, required = false, name = "user.password", schema = @Schema(type = "string", minLength = 8)),
//			@Parameter(in = ParameterIn.QUERY, required = false, name = "user.enabled", schema = @Schema(type = "boolean")) })
//	public AuthorityEntity patchAuthority(@Parameter(hidden = true) @Valid AuthorityEntity authority) {
//		log.info("authority:{}", authority);
//		return authority;
//	}
	/*

	@PatchMapping("/authority")
	@Operation(tags = {"Authority"})
	public AuthorityEntity patchAuthority(@ParameterObject @Valid AuthorityPatchParam source)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("source:{}", source);
		String content = this.mapper.writeValueAsString(source);
		log.info("content:{}", content);
		AuthorityEntity target = this.mapper.readValue(content, AuthorityEntity.class);
		log.info("target:{}", target);
		return target;
	}

	@PostMapping(path = "/user", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public UserEntity postUser(@Valid @RequestBody UserPutParam source)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("source:{}", source);
		String content = this.mapper.writeValueAsString(source);
		log.info("content:{}", content);
		UserEntity target = this.mapper.readValue(content, UserEntity.class);
		log.info("target:{}", target);
		return target;
	}

	@PutMapping(path = "/user")
	@ResponseStatus(HttpStatus.CREATED)
	@Parameters({
			@Parameter(in = ParameterIn.QUERY, required = true, name = "username", schema = @Schema(type = "string", pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")),
			@Parameter(in = ParameterIn.QUERY, required = true, name = "password", schema = @Schema(type = "string", minLength = 6)),
			@Parameter(in = ParameterIn.QUERY, required = false, name = "enabled", schema = @Schema(type = "boolean")),
			@Parameter(in = ParameterIn.QUERY, required = true, name = "authorities[0].id", schema = @Schema(type = "integer", format = "int64")) })
	public UserEntity putUserEntity(@Parameter(hidden = true) @Valid UserPutParam source)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("source:{}", source);
		String content = this.mapper.writeValueAsString(source);
		log.info("content:{}", content);
		UserEntity target = this.mapper.readValue(content, UserEntity.class);
		log.info("target:{}", target);
		return target;
	}

	@PatchMapping("/user")
	@Parameters({
			@Parameter(in = ParameterIn.QUERY, required = true, name = "username", schema = @Schema(type = "string", pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")),
			@Parameter(in = ParameterIn.QUERY, required = true, name = "password", schema = @Schema(type = "string", minLength = 6)),
			@Parameter(in = ParameterIn.QUERY, required = false, name = "enabled", schema = @Schema(type = "boolean")),
			@Parameter(in = ParameterIn.QUERY, required = true, name = "authorities[0].id", schema = @Schema(type = "integer", format = "int64")) })
	public UserEntity patchUserEntity(@Parameter(hidden = true) @Valid UserPutParam source)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("source:{}", source);
		String content = this.mapper.writeValueAsString(source);
		log.info("content:{}", content);
		UserEntity target = this.mapper.readValue(content, UserEntity.class);
		log.info("target:{}", target);
		return target;
	}*/

	@GetMapping("/page")
	@PageableAsQueryParam
	public String operation4(@Parameter(hidden = true) Pageable pageable) {
		log.info("pageable:{}", pageable);
		return "operation4";
	}

	@GetMapping("/search")
	public ResponseEntity<List<AuthorityEntity>> getAllPets(@ParameterObject Pageable pageable) {
		log.info("pageable:{}", pageable);
		return null;
	}

	/*
	 * 
	 * 
	 * 
	 * @PostMapping("/input/authorities") public List<AuthorityEntity>
	 * inputAuthorities(@RequestBody List<AuthorityEntity> authorities) { return
	 * authorities; }
	 * 
	 * @PostMapping("/input/array") public String[] inputArray(@RequestParam
	 * String[] array) { return array; }
	 */

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
