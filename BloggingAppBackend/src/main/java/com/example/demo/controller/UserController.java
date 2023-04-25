package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.payload.UserDTO;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	// POST-create user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
		UserDTO createUserDTO = userService.registerNewUser(user);
		return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
	}

	// PUT- update user

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody User user, @PathVariable("userId") Integer uid) throws UserException {
		UserDTO updatedUser = userService.updateUser(user, uid);
		return ResponseEntity.ok(updatedUser);
	}

	//ADMIN
	// DELETE -delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer uid) throws UserException {
		userService.deleteUser(uid);
		return new ResponseEntity<>("User deleted Successfully", HttpStatus.OK);
	}

	// GET - user get
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() throws UserException {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// GET - user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) throws UserException {
		return ResponseEntity.ok(userService.getUserById(userId));
	}


}
