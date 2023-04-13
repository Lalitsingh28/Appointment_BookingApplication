package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.AppConstants;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.UserDTO;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDTO registerNewUser(UserDTO UserDTO) {

		User user = this.modelMapper.map(UserDTO, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO UserDTO, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setName(UserDTO.getName());
		user.setEmail(UserDTO.getEmail());
		user.setPassword(UserDTO.getPassword());
		user.setAbout(UserDTO.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDTO UserDTO1 = this.userToDto(updatedUser);
		return UserDTO1;
	}

	@Override
	public UserDTO getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDTO> UserDTOs = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return UserDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}

	public User dtoToUser(UserDTO UserDTO) {
		User user = this.modelMapper.map(UserDTO, User.class);
		return user;
	}

	public UserDTO userToDto(User user) {
		UserDTO UserDTO = this.modelMapper.map(user, UserDTO.class);
		return UserDTO;
	}

	

}
