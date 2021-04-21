package asboot.auth.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Group")
@Entity(name = "groups")
@JsonInclude(value = Include.NON_EMPTY)
@XmlRootElement(name = "Group")
@JacksonXmlRootElement(localName = "Group")
public class GroupEntity implements Serializable {

	private static final long serialVersionUID = 1640084209593671173L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "group_name")
	private String name;

	@OneToMany(mappedBy = "group")
	@XmlElementWrapper(name = "members")
	@JacksonXmlElementWrapper(localName = "members")
	@JacksonXmlProperty(localName = "GroupMember")
	private List<GroupMemberEntity> members;

	@OneToMany(mappedBy = "group")
	@XmlElementWrapper(name = "authorities")
	@JacksonXmlElementWrapper(localName = "authorities")
	@JacksonXmlProperty(localName = "GroupAuthority")
	private List<GroupAuthorityEntity> authorities;

}
