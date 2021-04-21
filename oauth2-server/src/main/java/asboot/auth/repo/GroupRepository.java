package asboot.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asboot.auth.entity.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long>{
	
//	@Query("select a from #{#entityName} a where a.id = ?1")
//	Optional<GroupEntity> findById(Long id);

}
