package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.payload.UserDTO;

public interface UserService {
	
	public UserDTO registerNewUser(User user);

	public UserDTO updateUser(User user, Integer userId) throws UserException;
	
	public List<UserDTO> getAllUsers() throws UserException;

	public UserDTO getUserById(Integer userId) throws UserException;

	public String deleteUser(Integer userId) throws UserException;

}
