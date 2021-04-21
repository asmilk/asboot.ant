package asboot.auth.ctrl.uaa;

import java.util.Optional;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import asboot.auth.entity.UserEntity;
import asboot.auth.serv.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/uaa/user", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
		MediaType.APPLICATION_XML_VALUE })
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{username}")
	@Operation(tags = { "User" })
	public ResponseEntity<UserEntity> getUser(
			@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$") @PathVariable String username) {
		log.info("username:{}", username);
		Optional<UserEntity> optional = this.userService.findByUsername(username);
		return optional.isPresent() ? new ResponseEntity<UserEntity>(optional.get(), HttpStatus.OK)
				: new ResponseEntity<UserEntity>(HttpStatus.NO_CONTENT);
	}

}
