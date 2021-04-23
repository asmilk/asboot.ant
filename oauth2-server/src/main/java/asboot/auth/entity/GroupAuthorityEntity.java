package asboot.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GroupAuthority")
@Entity(name = "group_authorities")
@JsonInclude(value = Include.NON_EMPTY)
@XmlRootElement(name = "GroupAuthority")
@JacksonXmlRootElement(localName = "GroupAuthority")
public class GroupAuthorityEntity implements Serializable {

	private static final long serialVersionUID = 4162792575343896411L;

	@Id
	@Column(length = 11)
	@Size(min = 5, max = 11)
	private String authority;

	@ManyToOne
	@JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "FK_GROUP_AUTHORITIES__GROUP_ID"))
	@JsonBackReference
	@JacksonXmlProperty(localName = "Group")
	private GroupEntity group;

}
