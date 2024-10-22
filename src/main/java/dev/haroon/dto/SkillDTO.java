package dev.haroon.dto;


import dev.haroon.entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {

	
	private Integer skillId;
	
	private String skillName;
	
	private byte[] skillIcon;
	
	private String iconType;
	
	private String iconName;
	
	private String learnedFrom;
	
    private String point1;
	
	private String point2;
	
	private String point3;
	
	private String point4;
	
	private String point5;
	
	private String sourceLink;
	
	// foreign key reference from user table
	private Integer userId;
}
