package com.dsleandro.university.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dsleandro.university.entity.Role;
import com.dsleandro.university.entity.User;
import com.dsleandro.university.repository.RoleRepository;
import com.dsleandro.university.repository.UserRepository;

@Component
public class InitDB {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	private void postConstruct() {

		if(roleRepository.findByName("ADMIN") != null) return;

		Role newAdminRole = new Role();
		Role newStudentRole = new Role();

		newAdminRole.setName("ADMIN");
		newStudentRole.setName("STUDENT");

		roleRepository.save(newAdminRole);
		roleRepository.save(newStudentRole);

		Role adminRole = roleRepository.findByName("ADMIN");

		User adminUser = new User();
		adminUser.setDni("0000");
		adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
		adminUser.setFirstName("xx"); // required due to validation
		adminUser.setLastName("yy"); // required due to validation
		adminUser.setActive(true);
		adminUser.setRole(adminRole);
		userRepository.save(adminUser);

	}

}
