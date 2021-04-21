package asboot.auth.serv.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asboot.auth.entity.GroupAuthorityEntity;
import asboot.auth.entity.GroupEntity;
import asboot.auth.entity.GroupMemberEntity;
import asboot.auth.repo.GroupAuthorityRepository;
import asboot.auth.repo.GroupMemberRepository;
import asboot.auth.repo.GroupRepository;
import asboot.auth.serv.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private GroupAuthorityRepository groupAuthorityRepository;

	@Override
	public Optional<GroupEntity> findById(Long id) {
		return this.groupRepository.findById(id);
	}

	@Override
	public Optional<GroupMemberEntity> findByUsername(String username) {
		return this.groupMemberRepository.findByUsername(username);
	}

	@Override
	public Optional<GroupAuthorityEntity> findByAuthority(String authority) {
		return this.groupAuthorityRepository.findById(authority);
	}

}
