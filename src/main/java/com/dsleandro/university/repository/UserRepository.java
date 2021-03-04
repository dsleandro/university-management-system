package com.dsleandro.university.repository;

import java.util.Optional;

import com.dsleandro.university.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByDni(String userName);
}
