package asboot.auth.param;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class AuthorityPutParam implements Serializable {

	private static final long serialVersionUID = 1174728347944481686L;
	
	@NotNull
	private Long id;

}
