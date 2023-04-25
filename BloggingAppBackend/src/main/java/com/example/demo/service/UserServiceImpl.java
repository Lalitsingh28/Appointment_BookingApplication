package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.payload.UserDTO;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public UserDTO userToDto(User user) {
		UserDTO UserDTO = this.modelMapper.map(user, UserDTO.class);
		return UserDTO;
	}
	
	@Override
	public UserDTO registerNewUser(User user) {

		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 User savedUser = userRepo.save(user);
		 UserDTO userDto = userToDto(savedUser);
		 return userDto;
		
	}

	@Override
	public UserDTO updateUser(User User, Integer userId) throws UserException{

		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			user.get().setName(User.getName());
			user.get().setEmail(User.getEmail());
			user.get().setPassword(User.getPassword());
			user.get().setAbout(User.getAbout());
			
			UserDTO userDto = userToDto(user.get());
			return userDto;
			
		}else {
			throw new UserException("There is no user with Id : "+userId);
		}
	}

	@Override
	public UserDTO getUserById(Integer userId) throws UserException {
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			UserDTO userDto = userToDto(user.get());
			return userDto;
			
		}else {
			throw new UserException("There is no user with Id : "+userId);
		}
	}

	@Override
	public List<UserDTO> getAllUsers() throws UserException{

		List<User> users = userRepo.findAll();
		if(!users.isEmpty()) {
		List<UserDTO> UserDTOs = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return UserDTOs;
		}else {
			throw new UserException("There are no users available");
		}
	}

	@Override
	public String deleteUser(Integer userId) throws UserException{
		Optional<User> user = userRepo.findById(userId);
		if(user.isPresent()) {
			return "User with ID :"+ userId + " has been deleted";
			
		}else {
			throw new UserException("There is no user with Id : "+userId);
		}
		

	}

	
}
