package asboot.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asboot.auth.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

//	@Query("select a from #{#entityName} a where a.username = ?1")
//	Optional<UserEntity> findByUsername(String username);

}
