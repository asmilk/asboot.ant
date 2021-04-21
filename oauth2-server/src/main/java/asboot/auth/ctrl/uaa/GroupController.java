package asboot.auth.ctrl.uaa;

import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import asboot.auth.entity.GroupAuthorityEntity;
import asboot.auth.entity.GroupEntity;
import asboot.auth.entity.GroupMemberEntity;
import asboot.auth.serv.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/uaa/group", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
		MediaType.APPLICATION_XML_VALUE })
public class GroupController {

	@Autowired
	private GroupService groupService;

	@GetMapping("/{id}")
	@Operation(tags = { "Group" })
	public ResponseEntity<GroupEntity> getGroup(@Min(value = 1) @PathVariable Long id) {
		log.info("id:{}", id);
		Optional<GroupEntity> optional = this.groupService.findById(id);
		return optional.isPresent() ? new ResponseEntity<GroupEntity>(optional.get(), HttpStatus.OK)
				: new ResponseEntity<GroupEntity>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/member/{username}")
	@Operation(tags = { "Group" })
	public ResponseEntity<GroupMemberEntity> getGroupMember(
			@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$") @PathVariable String username) {
		log.info("username:{}", username);
		Optional<GroupMemberEntity> optional = this.groupService.findByUsername(username);
		return optional.isPresent() ? new ResponseEntity<GroupMemberEntity>(optional.get(), HttpStatus.OK)
				: new ResponseEntity<GroupMemberEntity>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/authority/{authority}")
	@Operation(tags = { "Group" })
	public ResponseEntity<GroupAuthorityEntity> getGroupAuthority(
			@Size(min = 5, max = 11) @PathVariable String authority) {
		log.info("authority:{}", authority);
		Optional<GroupAuthorityEntity> optional = this.groupService.findByAuthority(authority);
		return optional.isPresent() ? new ResponseEntity<GroupAuthorityEntity>(optional.get(), HttpStatus.OK)
				: new ResponseEntity<GroupAuthorityEntity>(HttpStatus.NO_CONTENT);
	}

}
