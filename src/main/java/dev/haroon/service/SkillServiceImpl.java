package dev.haroon.service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ImageResponseDTO;
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
	public Integer createSkill(SkillDTO skillDTO, MultipartFile skillIcon) throws NoResourceFoundException, IOException {
		
		Integer userId = skillDTO.getUserId();
		
		User user = userRepo.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found!"));
		Skill skill = dtoToSkill(skillDTO, user);
		skill.setSkillIcon(skillIcon.getBytes());
		skill.setIconType(skillIcon.getContentType());
		skill.setIconName(skillIcon.getOriginalFilename());
		
		skill = skillRepo.save(skill);
		return skill.getSkillId();
	}

	@Override
	public Set<SkillDTO> getAllSkills(Integer userId) throws NoResourceFoundException {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found!"));
		
		Set<Skill> skills = skillRepo.findByUserUserId(userId);
	 
		
		return skills.stream().map(this::skillToDTO).collect(Collectors.toSet());
	}

	@Override
	public Integer updateSkill(SkillDTO skillDTO, MultipartFile file) throws NoResourceFoundException, IOException {

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
	    skill.setSourceLink(skillDTO.getSourceLink());
	    
	    if(file != null) {
	    	skill.setSkillIcon(file.getBytes());
	    	skill.setIconType(file.getContentType());
	    	skill.setIconName(file.getOriginalFilename());
	    }

	    // Save the updated entity
	    skill = skillRepo.save(skill);
	    return skill.getSkillId();
	}

	@Override
	public void deleteSkill(Integer userId, Integer skillId) throws NoResourceFoundException {
	
		Skill skill = skillRepo.findById(skillId).orElseThrow(() -> new NoResourceFoundException("Skill Not found"));
		
		if(!skill.getUser().getUserId().equals(userId)) {
			 throw new NoResourceFoundException("You are not authorized to delete this skill");
		}
		
		skillRepo.delete(skill);
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
		skill.setSourceLink(skillDTO.getSourceLink());
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
		skillDTO.setSourceLink(skill.getSourceLink());
		skillDTO.setSkillIcon(skill.getSkillIcon());
		skillDTO.setIconType(skill.getIconType());
		skillDTO.setIconName(skill.getIconName());
		return skillDTO;
	}

	@Override
	public ImageResponseDTO getSkillIconByName(Integer userId, String skillName) throws NoResourceFoundException {
		Skill skill = skillRepo.findByUserUserIdAndSkillName(userId, skillName);
		
		ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
		
		System.out.println("skill which was fetched user and name => " + skill);
		if(skill != null) {
			imageResponseDTO.setFileName(skill.getIconName());
			imageResponseDTO.setImageData(skill.getSkillIcon());
			imageResponseDTO.setImageType(skill.getIconType());
		} else {
			throw new NoResourceFoundException("skill Not found for this userid and skillname");
		}
		
		return imageResponseDTO;
	}
}
