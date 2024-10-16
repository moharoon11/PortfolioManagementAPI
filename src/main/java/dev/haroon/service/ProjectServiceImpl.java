package dev.haroon.service;

import dev.haroon.dto.ProjectDTO;
import dev.haroon.entities.Project;
import dev.haroon.entities.User;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.repository.ProjectRepo;
import dev.haroon.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepository;

    @Autowired
    private UserRepo userRepository;

    // Convert ProjectDTO to Project entity
    private Project convertToProject(ProjectDTO projectDTO, MultipartFile file) throws IOException {
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectDescription(projectDTO.getProjectDescription());
        project.setLiveLink(projectDTO.getLiveLink());
        project.setCodeLink(projectDTO.getCodeLink());
        project.setTechnology(projectDTO.getTechnology());
        if (file != null && !file.isEmpty()) {
            project.setImageDate(file.getBytes()); // Set image data
            project.setImageType(file.getContentType());
            project.setImageName(file.getOriginalFilename());
        }

        // Set the user (Assuming projectDTO has userId)
        User user = userRepository.findById(projectDTO.getUserId())
                .orElseThrow(() -> new NoResourceFoundException("User not found"));
        project.setUser(user);

        return project;
    }

    // Convert Project entity to ProjectDTO
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setImageDate(project.getImageDate());
        projectDTO.setImageName(project.getImageName());
        projectDTO.setImageType(project.getImageType());
        projectDTO.setProjectDescription(project.getProjectDescription());
        projectDTO.setLiveLink(project.getLiveLink());
        projectDTO.setCodeLink(project.getCodeLink());
        projectDTO.setUserId(project.getUser().getUserId());  // Set userId
        projectDTO.setTechnology(project.getTechnology());
        return projectDTO;
    }

    @Override
    public Integer createProjectWithFile(ProjectDTO projectDTO, MultipartFile file) throws IOException {
        try {
            Project project = convertToProject(projectDTO, file);
            Project savedProject = projectRepository.save(project);
            return savedProject.getProjectId();
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

    @Override
    public Integer updateProject(ProjectDTO projectDTO, MultipartFile file) throws IOException{
        try {
            Project existingProject = projectRepository.findById(projectDTO.getProjectId())
                    .orElseThrow(() -> new NoResourceFoundException("Project not found"));

            existingProject.setProjectName(projectDTO.getProjectName());
            existingProject.setProjectDescription(projectDTO.getProjectDescription());
            existingProject.setLiveLink(projectDTO.getLiveLink());
            existingProject.setCodeLink(projectDTO.getCodeLink());
            existingProject.setTechnology(projectDTO.getTechnology());
            // If a new file is provided, update the image
            if (file != null && !file.isEmpty()) {
                existingProject.setImageDate(file.getBytes());
                existingProject.setImageName(file.getOriginalFilename());
                existingProject.setImageType(file.getContentType());
            }

            Project updatedProject = projectRepository.save(existingProject);
            return updatedProject.getProjectId();
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

    @Override
    public boolean deleteProject(Integer userId, Integer projectId) throws NoResourceFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoResourceFoundException("Project not found"));

        // Check if the project belongs to the user
        if (!project.getUser().getUserId().equals(userId)) {
            throw new NoResourceFoundException("You are not authorized to delete this project");
        }

        projectRepository.delete(project);
        return true;
    }


    @Override
    public List<ProjectDTO> getProjectsByUserId(Integer userId) {
        List<Project> projects = projectRepository.findByUserUserId(userId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

	@Override
	public Project getProjectById(Integer productId) {
		return projectRepository.findById(productId).get();
	}
}
