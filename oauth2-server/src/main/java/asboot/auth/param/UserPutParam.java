package asboot.auth.param;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class UserPutParam extends UserParam {

	private static final long serialVersionUID = 3254461286377821309L;

	@NotBlank
	@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")
	private String username;

	@NotBlank
	@Size(min = 6)
	private String password;

	@NotEmpty
	private List<AuthorityPutParam> authorities;

}
