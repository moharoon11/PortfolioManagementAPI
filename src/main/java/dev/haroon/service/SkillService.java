package dev.haroon.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.SkillDTO;
import dev.haroon.exceptions.NoResourceFoundException;

public interface SkillService {
	
	public Integer createSkill(SkillDTO skillDTO, MultipartFile skillIcon) throws NoResourceFoundException, IOException;
	
	public Set<SkillDTO> getAllSkills(Integer userId) throws NoResourceFoundException;
	
	public Integer updateSkill(SkillDTO skillDTO, MultipartFile file) throws NoResourceFoundException, IOException;
	
	public void deleteSkill(Integer userId, Integer skillId) throws NoResourceFoundException;
	
	public ImageResponseDTO getSkillIconByName(Integer userId, String skillName) throws NoResourceFoundException;

}
