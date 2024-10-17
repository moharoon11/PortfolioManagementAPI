package dev.haroon.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	
	@Lob
	private byte[] userImage1;
	
	private String userImage1Type;
	
	private String userImage1FileName;
	
	@Lob
	private byte[] userImage2;
	
    private String userImage2Type;
	
	private String userImage2FileName;
	
	@Lob
	private byte[] userImage3;
	
   private String userImage3Type;
	
	private String userImage3FileName;
	
	@Lob
	private byte[] resume;
	
	private String resumeType;
	
	private String resumeName;


	
	
}
