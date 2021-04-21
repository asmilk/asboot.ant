package asboot.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import asboot.auth.entity.GroupMemberEntity;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Long>{
	
	@Query("select a from #{#entityName} a where a.user.username = ?1")
	Optional<GroupMemberEntity> findByUsername(String username);

}
