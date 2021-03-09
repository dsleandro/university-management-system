package com.dsleandro.university.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import com.dsleandro.university.entity.Role;
import com.dsleandro.university.entity.User;
import com.dsleandro.university.repository.RoleRepository;
import com.dsleandro.university.repository.UserRepository;
import com.dsleandro.university.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByDni(String dni) {
		return userRepository.findByDni(dni).orElse(null);
	}

	@Override
	public void saveUser(User user) {

		if (!user.isActive()) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setActive(true);
			
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByName("STUDENT"));
			user.setRoles(roles);
		}

		userRepository.save(user);

	}

}
