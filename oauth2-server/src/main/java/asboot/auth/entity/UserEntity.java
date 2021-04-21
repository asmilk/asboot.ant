package asboot.auth.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User")
@Entity(name = "users")
@JsonInclude(value = Include.NON_EMPTY)
@XmlRootElement(name = "User")
@JacksonXmlRootElement(localName = "User")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -3239618935152846776L;

	@Id
	@Pattern(regexp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")
	private String username;

	@Size(min = 6)
	private String password;

	private Boolean enabled;

	@Parameter(hidden = true)
	@Schema(hidden = true)
	@OneToMany(mappedBy = "user")
	@XmlElementWrapper(name = "authorities")
	@JacksonXmlElementWrapper(localName = "authorities")
	@JacksonXmlProperty(localName = "Authority")
	private List<AuthorityEntity> authorities;

	@Parameter(hidden = true)
	@Schema(hidden = true)
	@OneToOne(mappedBy = "user")
	@JsonBackReference
	@JacksonXmlProperty(localName = "GroupMember")
	private GroupMemberEntity member;
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

}
