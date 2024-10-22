package dev.haroon.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer skillId;
	
	@Lob
	private byte[] skillIcon;
	
	private String iconType;
	
	private String iconName;
	
	private String skillName;
	
	private String learnedFrom;
	
	private String point1;
	
	private String point2;
	
	private String point3;
	
	private String point4;
	
	private String point5;
	
	private String sourceLink;
	
	  // many skills can have one user
	  @ManyToOne
	  @JoinColumn(name = "user_id", nullable = false) // Foreign key column in Project table
	  private User user;
    
}
