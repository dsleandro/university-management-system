package com.dsleandro.university.service.impl;

import java.util.Collections;

import com.dsleandro.university.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {

        com.dsleandro.university.entity.User user = userRepository.findByDni(dni).orElseThrow(()-> new UsernameNotFoundException("Invalid username"));

        return new User(dni, user.getPassword(), Collections.emptyList());

    }

}
