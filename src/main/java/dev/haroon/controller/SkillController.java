package dev.haroon.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.SkillDTO;
import dev.haroon.service.SkillService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

	
	@Autowired
	SkillService skillService;
	
	@PostMapping("/create")
	public ResponseEntity<Integer> createSkill(@RequestBody SkillDTO skillDTO) {
		return ResponseEntity.ok(skillService.createSkill(skillDTO));
	}
	
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Set<SkillDTO>> getAllSkills(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(skillService.getAllSkills(userId));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Integer> updateSkill(@RequestBody SkillDTO skillDTO) {
		return ResponseEntity.ok(skillService.updateSkill(skillDTO));
	}
	
	@DeleteMapping("/delete/{userId}/{skillId}")
	public ResponseEntity<ApiResponse> deleteSkill(@PathVariable("userId") Integer userId, @PathVariable("skillId") Integer skillId) {
		skillService.deleteSkill(userId, skillId);
		return ResponseEntity.ok(new ApiResponse("Skill deleted sucessfully!", true));
	}
	
	
}
