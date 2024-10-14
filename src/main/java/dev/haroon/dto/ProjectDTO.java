package dev.haroon.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDTO {

    private Integer projectId;
    private String projectName;
    private String projectDescription;
    private byte[] projectImage;
    private String liveLink;
    private String codeLink;
    private String technology;
    
    // User ID (since we're fetching projects based on the user)
    private Integer userId;

}
 