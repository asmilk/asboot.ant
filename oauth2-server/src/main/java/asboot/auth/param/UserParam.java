package asboot.auth.param;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UserParam")
@XmlRootElement(name = "User")
@JsonInclude(value = Include.NON_NULL)
public class UserParam implements Serializable {

	private static final long serialVersionUID = -850964664642433893L;

	@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")
	private String username;

	@Size(min = 6)
	private String password;

	private Boolean enabled;

}
