package dev.haroon.controller;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ProjectDTO;
import dev.haroon.entities.Project;
import dev.haroon.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create a new project with file upload
    @PostMapping("/create")
    public ResponseEntity<Integer> createProject(
            @RequestPart("projectDTO") ProjectDTO projectDTO, 
            @RequestPart("file") MultipartFile file) throws IOException {

        Integer projectId = projectService.createProjectWithFile(projectDTO, file);
        return ResponseEntity.ok(projectId);
    }

    // Get all projects by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUserId(@PathVariable Integer userId) {
        List<ProjectDTO> projects = projectService.getProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }
    
    
     @GetMapping("/image/{projectId}")
     public ResponseEntity<byte[]> getImageByProjectId(@PathVariable int projectId) {
    	 
    	 Project project = projectService.getProjectById(projectId);
    	 
    	 byte[] imageFile = project.getImageDate();
    	 
    	 return ResponseEntity.ok().contentType(MediaType.valueOf(project.getImageType())).body(imageFile);
     }
    

    // Update a project by ID
    @PutMapping("/update")
    public ResponseEntity<Integer> updateProject(
            @RequestPart("projectDTO") ProjectDTO projectDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        Integer updatedProjectId = projectService.updateProject(projectDTO, file);
        return ResponseEntity.ok(updatedProjectId);
    }

    // Delete a project by ID
 // Delete a project by user ID and project ID
    @DeleteMapping("/delete/{userId}/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Integer userId, @PathVariable Integer projectId) {
        boolean isDeleted = projectService.deleteProject(userId, projectId);
        return ResponseEntity.ok(new ApiResponse("Project deleted sucessfully!", true));
    }

}
