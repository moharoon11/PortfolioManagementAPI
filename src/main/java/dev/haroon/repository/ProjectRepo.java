package dev.haroon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.haroon.entities.Project;

public interface ProjectRepo extends JpaRepository<Project, Integer>{

	   List<Project> findByUserUserId(Integer userId);
	   
	   
	   
	 
}
