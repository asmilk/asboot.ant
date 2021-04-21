package asboot.auth.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GroupMember")
@Entity(name = "group_members")
@JsonInclude(value = Include.NON_EMPTY)
@XmlRootElement(name = "GroupMember")
@JacksonXmlRootElement(localName = "GroupMember")
public class GroupMemberEntity implements Serializable {

	private static final long serialVersionUID = -4612045325998934145L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "username", unique = true)
	@JacksonXmlProperty(localName = "User")
	private UserEntity user;

	@ManyToOne
	@JsonBackReference
	@JacksonXmlProperty(localName = "Group")
	private GroupEntity group;

}
