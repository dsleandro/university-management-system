package com.dsleandro.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsleandro.university.entity.Role;
import com.dsleandro.university.repository.RoleRepository;
import com.dsleandro.university.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findRoleByName(String name) {

		return roleRepository.findByName(name);
	}

}
