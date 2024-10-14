package dev.haroon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Integer userId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	private String about;


}
