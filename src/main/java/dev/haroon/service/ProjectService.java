package dev.haroon.service;

import dev.haroon.dto.ProjectDTO;
import dev.haroon.entities.Project;
import dev.haroon.exceptions.NoResourceFoundException;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
	
    Integer createProjectWithFile(ProjectDTO projectDTO, MultipartFile file) throws IOException;

    Integer updateProject(ProjectDTO projectDTO, MultipartFile file) throws IOException;


    List<ProjectDTO> getProjectsByUserId(Integer userId);

	boolean deleteProject(Integer userId, Integer projectId) throws NoResourceFoundException;

	Project getProjectById(Integer productId);

}
