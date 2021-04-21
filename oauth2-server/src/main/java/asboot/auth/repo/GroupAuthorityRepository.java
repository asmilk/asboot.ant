package asboot.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asboot.auth.entity.GroupAuthorityEntity;

@Repository
public interface GroupAuthorityRepository extends JpaRepository<GroupAuthorityEntity, String>{

}
