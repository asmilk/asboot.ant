package asboot.auth.param;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = Include.NON_NULL)
public class AuthorityPatchParam extends AuthorityParam {

	private static final long serialVersionUID = 4390731176385391917L;

	@NotNull
	private Long id;

}
