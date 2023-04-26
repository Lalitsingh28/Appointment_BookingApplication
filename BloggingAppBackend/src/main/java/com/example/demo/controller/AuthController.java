package com.example.demo.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.CustomUser;
import com.example.demo.entity.User;
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
	private PasswordEncoder passwordEncoder;


//	login
	
	@PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> getToken(@RequestBody JwtAuthRequest authRequest) throws Exception {
        // Get user details
        UserDetails userDetails = customUser.loadUserByUsername(authRequest.getUsername());

        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){  
			try {
				// Generate token
				String token = jwtUtils.generateToken(authRequest.getUsername());
				
				// response
	            JwtAuthResponse response = new JwtAuthResponse();
	            response.setToken(token);
	            response.setUser(mapper.map(userDetails, UserDTO.class));
	            return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.ACCEPTED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        throw new Exception("User details invalid.");
    }


	// get logged in user data

	@GetMapping("/currentuser")
	public ResponseEntity<UserDTO> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName());
		return new ResponseEntity<UserDTO>(mapper.map(user, UserDTO.class), HttpStatus.OK);
	}

}
