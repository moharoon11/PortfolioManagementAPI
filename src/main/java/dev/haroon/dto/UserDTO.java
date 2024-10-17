package dev.haroon.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@Id
	private Integer userId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	private String about;
	
	private byte[] userImage1;
	
	private String userImage1Type;
	
	private String userImage1FileName;
	
	
	private byte[] userImage2;
	
    private String userImage2Type;
	
	private String userImage2FileName;
	

	private byte[] userImage3;
	
   private String userImage3Type;
	
	private String userImage3FileName;
	

	private byte[] resume;
	
	private String resumeType;
	
	private String resumeName;


}
