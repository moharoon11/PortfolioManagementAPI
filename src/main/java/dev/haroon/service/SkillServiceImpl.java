package dev.haroon.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.haroon.dto.SkillDTO;
import dev.haroon.repository.SkillRepo;
import dev.haroon.repository.UserRepo;
import dev.haroon.entities.Skill;
import dev.haroon.entities.User;
import dev.haroon.exceptions.NoResourceFoundException;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepo skillRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public Integer createSkill(SkillDTO skillDTO) {
		
		Integer userId = skillDTO.getUserId();
		
		User user = userRepo.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found!"));
		Skill skill = dtoToSkill(skillDTO, user);
		skill = skillRepo.save(skill);
		return skill.getSkillId();
	}

	@Override
	public Set<SkillDTO> getAllSkills(Integer userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found!"));
		
		Set<Skill> skills = skillRepo.findByUserUserId(userId);
	
		
		return skills.stream().map(this::skillToDTO).collect(Collectors.toSet());
	}

	@Override
	public Integer updateSkill(SkillDTO skillDTO) {

	    // Fetch the existing skill from the database
	    Skill skill = skillRepo.findById(skillDTO.getSkillId())
	            .orElseThrow(() -> new NoResourceFoundException("Skill Not found"));
	    
	    // Check if the user is authorized to update the skill
	    if (!skill.getUser().getUserId().equals(skillDTO.getUserId())) {
	        throw new NoResourceFoundException("You are not authorized to update this skill");
	    }

	    // Update the fields of the existing skill entity
	    skill.setSkillName(skillDTO.getSkillName());
	    skill.setLearnedFrom(skillDTO.getLearnedFrom());
	    skill.setPoint1(skillDTO.getPoint1());
	    skill.setPoint2(skillDTO.getPoint2());
	    skill.setPoint3(skillDTO.getPoint3());
	    skill.setPoint4(skillDTO.getPoint4());
	    skill.setPoint5(skillDTO.getPoint5());

	    // Save the updated entity
	    skill = skillRepo.save(skill);
	    return skill.getSkillId();
	}

	@Override
	public boolean deleteSkill(Integer userId, Integer skillId) {
	
		Skill skill = skillRepo.findById(skillId).orElseThrow(() -> new NoResourceFoundException("Skill Not found"));
		
		if(!skill.getUser().getUserId().equals(userId)) {
			 throw new NoResourceFoundException("You are not authorized to delete this skill");
		}
		
		skillRepo.delete(skill);
		return true;
	}

	private Skill dtoToSkill(SkillDTO skillDTO, User user) {
		Skill skill = new Skill();
		skill.setSkillName(skillDTO.getSkillName());
		skill.setLearnedFrom(skillDTO.getLearnedFrom());
		skill.setUser(user);
		skill.setPoint1(skillDTO.getPoint1());
		skill.setPoint2(skillDTO.getPoint2());
		skill.setPoint3(skillDTO.getPoint3());
		skill.setPoint4(skillDTO.getPoint4());
		skill.setPoint5(skillDTO.getPoint5());
		return skill;
	}
	
	private SkillDTO skillToDTO(Skill skill) {
		SkillDTO skillDTO = new SkillDTO();
		
		skillDTO.setUserId(skill.getUser().getUserId());
		skillDTO.setSkillId(skill.getSkillId());
		skillDTO.setSkillName(skill.getSkillName());
		skillDTO.setLearnedFrom(skill.getLearnedFrom());
		skillDTO.setPoint1(skill.getPoint1());
		skillDTO.setPoint2(skill.getPoint2());
		skillDTO.setPoint3(skill.getPoint3());
		skillDTO.setPoint4(skill.getPoint4());
		skillDTO.setPoint5(skill.getPoint5());
		return skillDTO;
	}
}
