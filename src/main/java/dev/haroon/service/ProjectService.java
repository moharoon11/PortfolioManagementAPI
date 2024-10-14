package dev.haroon.service;

import dev.haroon.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
	
    Integer createProjectWithFile(ProjectDTO projectDTO, MultipartFile file);

    Integer updateProject(ProjectDTO projectDTO, MultipartFile file);


    List<ProjectDTO> getProjectsByUserId(Integer userId);

	boolean deleteProject(Integer userId, Integer projectId);
}
