package asboot.auth.serv.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asboot.auth.entity.UserEntity;
import asboot.auth.repo.UserRepository;
import asboot.auth.serv.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return this.userRepository.findById(username);
	}

}
