package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.UserDTO;

public interface UserService {
	
	public UserDTO registerNewUser(UserDTO user);
	
	public UserDTO createUser(UserDTO user);

	public UserDTO updateUser(UserDTO user, Integer userId);
	
	public List<UserDTO> getAllUsers();

	public UserDTO getUserById(Integer userId);

	public void deleteUser(Integer userId);

}
