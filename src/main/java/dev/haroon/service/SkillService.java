package dev.haroon.service;

import java.util.Set;

import dev.haroon.dto.SkillDTO;
import dev.haroon.exceptions.NoResourceFoundException;

public interface SkillService {
	
	public Integer createSkill(SkillDTO skillDTO) throws NoResourceFoundException;
	
	public Set<SkillDTO> getAllSkills(Integer userId) throws NoResourceFoundException;
	
	public Integer updateSkill(SkillDTO skillDTO) throws NoResourceFoundException;
	
	public void deleteSkill(Integer userId, Integer skillId) throws NoResourceFoundException;

}
