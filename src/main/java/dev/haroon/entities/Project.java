package dev.haroon.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    private String projectName;

    private String projectDescription;

    @Lob
	private byte[] imageDate;
    
    private String imageName;
    
    private String imageType;

    private String liveLink;

    private String codeLink;
    
    private String technology;

    // Many-to-One relationship with User (Foreign key: userId)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column in Project table
    private User user;

}
