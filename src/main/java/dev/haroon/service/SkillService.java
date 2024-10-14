package dev.haroon.service;

import java.util.Set;

import dev.haroon.dto.SkillDTO;

public interface SkillService {
	
	public Integer createSkill(SkillDTO skillDTO);
	
	public Set<SkillDTO> getAllSkills(Integer userId);
	
	public Integer updateSkill(SkillDTO skillDTO);
	
	public boolean deleteSkill(Integer userId, Integer skillId);

}
