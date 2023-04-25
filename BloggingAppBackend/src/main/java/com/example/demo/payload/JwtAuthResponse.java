package com.example.demo.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	
	private UserDTO user;

}
