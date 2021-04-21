 package asboot.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asboot.auth.entity.AuthorityEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {

//	@Query("select a from #{#entityName} a where a.authority = ?1")
//	Optional<AuthorityEntity> findById(String authority);

}
