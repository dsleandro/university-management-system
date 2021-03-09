package com.dsleandro.university.service.impl;

import com.dsleandro.university.repository.UserRepository;
import com.dsleandro.university.entity.CustomUserDetails;
import com.dsleandro.university.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
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

        User user = userRepository.findByDni(dni)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

        return new CustomUserDetails(user);

    }

}
