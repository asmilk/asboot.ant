package asboot.auth.ctrl.uaa;

import java.util.Optional;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import asboot.auth.entity.AuthorityEntity;
import asboot.auth.serv.AuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/uaa/authority", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
		MediaType.APPLICATION_XML_VALUE })
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;

	@GetMapping("/{authority}")
	@Operation(tags = { "Authority" })
	public ResponseEntity<AuthorityEntity> getAuthority(@Size(min = 5, max = 11) @PathVariable String authority) {
		log.info("authority:{}", authority);
		Optional<AuthorityEntity> optional = this.authorityService.findByAuthority(authority);
		return optional.isPresent() ? new ResponseEntity<AuthorityEntity>(optional.get(), HttpStatus.OK)
				: new ResponseEntity<AuthorityEntity>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(consumes = { MediaType.ALL_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	@Operation(tags = { "Authority" })
	public ResponseEntity<AuthorityEntity> postAuthority(@Validated @ModelAttribute AuthorityEntity authority) {
		log.info("authority:{}", authority);
		return new ResponseEntity<AuthorityEntity>(this.authorityService.save(authority), HttpStatus.CREATED);
	}

}
