package asboot.auth.serv;

import java.util.Optional;

import javax.transaction.Transactional;

import asboot.auth.entity.UserEntity;

@Transactional
public interface UserService {
	
	Optional<UserEntity> findByUsername(String username);

}
