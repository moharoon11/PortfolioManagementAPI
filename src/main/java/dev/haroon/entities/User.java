package dev.haroon.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	private Integer userId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	private String about;


	
	
}
