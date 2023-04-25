package com.example.demo.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.CustomUser;
import com.example.demo.entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.payload.JwtAuthRequest;
import com.example.demo.payload.JwtAuthResponse;
import com.example.demo.payload.UserDTO;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JwtUtils;

@RestController
@RequestMapping("/user")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private CustomUser customUser;


	@Autowired
	private AuthenticationManager authenticationManager;


//	login
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = customUser.loadUserByUsername(request.getUsername());
		String token = jwtUtils.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(mapper.map(userDetails, UserDTO.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
//			System.out.println("Invalid Detials !!");
			throw new UserException("Invalid username or password !!");
		}

	}


	// get loggedin user data

	@GetMapping("/currentuser")
	public ResponseEntity<UserDTO> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName());
		return new ResponseEntity<UserDTO>(this.mapper.map(user, UserDTO.class), HttpStatus.OK);
	}

}
