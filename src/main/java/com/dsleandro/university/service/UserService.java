package com.dsleandro.university.service;

import com.dsleandro.university.entity.User;

public interface UserService {
	
	public User findUserByDni(String dni);
	
	public void saveUser(User user);
}
