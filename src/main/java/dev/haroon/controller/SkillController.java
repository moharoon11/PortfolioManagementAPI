package dev.haroon.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.SkillDTO;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.service.SkillService;
import dev.haroon.service.UserService;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

	
	@Autowired
	SkillService skillService;
	
	@PostMapping("/create")
	public ResponseEntity<Integer> createSkill(@RequestPart SkillDTO skillDTO, @RequestPart MultipartFile skillIcon) throws NoResourceFoundException, IOException {
		return ResponseEntity.ok(skillService.createSkill(skillDTO, skillIcon));
	}
	
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Set<SkillDTO>> getAllSkills(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(skillService.getAllSkills(userId));
	}
	
	@PutMapping("/update")
	public ResponseEntity<Integer> updateSkill(@RequestPart SkillDTO skillDTO,@RequestPart(required=false) MultipartFile skillIcon) throws NoResourceFoundException, IOException {
		return ResponseEntity.ok(skillService.updateSkill(skillDTO, skillIcon));
	}
	
	@DeleteMapping("/delete/{userId}/{skillId}")
	public ResponseEntity<ApiResponse> deleteSkill(@PathVariable("userId") Integer userId, @PathVariable("skillId") Integer skillId) {
		skillService.deleteSkill(userId, skillId);
		return ResponseEntity.ok(new ApiResponse("Skill deleted sucessfully!", true));
	}
	
	@GetMapping("/getSkillIcon/{userId}/{skillName}")
	public ResponseEntity<ImageResponseDTO> getSkillIcon(@PathVariable("userId") Integer userId, @PathVariable("skillName") String skillName) {
		return ResponseEntity.ok(skillService.getSkillIconByName(userId, skillName));
	}
	
	
}
