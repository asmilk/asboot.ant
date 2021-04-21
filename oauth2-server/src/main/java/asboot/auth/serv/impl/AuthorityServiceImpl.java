package asboot.auth.serv.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asboot.auth.entity.AuthorityEntity;
import asboot.auth.repo.AuthorityRepository;
import asboot.auth.serv.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Optional<AuthorityEntity> findByAuthority(String authority) {
		return this.authorityRepository.findById(authority);
	}

	@Override
	public AuthorityEntity save(AuthorityEntity entity) {
		//Optional<AuthorityEntity> optional = this.findByAuthority(entity.getAuthority());
		//optional.isPresent()
		return this.authorityRepository.save(entity);
	}

}
