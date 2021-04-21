package asboot.auth.serv;

import java.util.Optional;

import javax.transaction.Transactional;

import asboot.auth.entity.GroupAuthorityEntity;
import asboot.auth.entity.GroupEntity;
import asboot.auth.entity.GroupMemberEntity;

@Transactional
public interface GroupService {
	
	Optional<GroupEntity> findById(Long id);
	
	Optional<GroupMemberEntity> findByUsername(String username);
	
	Optional<GroupAuthorityEntity> findByAuthority(String authority);

}
