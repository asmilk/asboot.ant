package asboot.auth.serv;

import java.util.Optional;

import javax.transaction.Transactional;

import asboot.auth.entity.AuthorityEntity;

@Transactional
public interface AuthorityService {

	Optional<AuthorityEntity> findByAuthority(String authority);

	AuthorityEntity save(AuthorityEntity entity);

}
