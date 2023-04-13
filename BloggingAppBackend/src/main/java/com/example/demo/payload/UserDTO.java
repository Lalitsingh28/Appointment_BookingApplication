package com.example.demo.payload;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private int id;

	@NotEmpty
	@Size(min = 5, message = "Username must be min of 5 characters")
	private String name;

	@Email(message = "Invalid Email")
	@NotEmpty(message = "Email Section Cannot be Empty")
	private String email;

	@NotEmpty
	@Size(min = 6, message = "Password must be min of 6 characters")
	private String password;

	@NotEmpty
	private String about;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	
//	@JsonIgnore
//	public String getPassword() {
//		return this.password;
//	}
	
//	@JsonProperty
//	public void setPassword(String password) {
//		this.password=password;
//	}


}
