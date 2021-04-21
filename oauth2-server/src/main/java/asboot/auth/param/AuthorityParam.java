package asboot.auth.param;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AuthorityParam")
@XmlRootElement(name = "Authority")
@JsonInclude(value = Include.NON_NULL)
public class AuthorityParam implements Serializable {

	private static final long serialVersionUID = 1424676499436822052L;
	
	private Long id;

	@NotBlank
	@Size(min = 5, max = 11)
	private String authority;

	@Valid
	@JacksonXmlProperty(localName = "User")
	private UserParam user;

}
