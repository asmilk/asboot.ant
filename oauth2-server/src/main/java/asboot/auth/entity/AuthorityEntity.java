package asboot.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Authority")
@Entity(name = "authorities")
@JsonInclude(value = Include.NON_EMPTY)
@XmlRootElement(name = "Authority")
@JacksonXmlRootElement(localName = "Authority")
public class AuthorityEntity implements Serializable {

	private static final long serialVersionUID = -8501938302723407482L;

	@Id
	@Column(length = 11)
	@Size(min = 5, max = 11)
	private String authority;

	@Valid
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "username")
	@JacksonXmlProperty(localName = "User")
	@Parameter(hidden = true)
	@Schema(hidden = true)
	private UserEntity user;

}
