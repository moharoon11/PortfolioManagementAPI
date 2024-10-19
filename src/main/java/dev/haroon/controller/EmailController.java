package dev.haroon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.haroon.dto.EmailDTO;
import dev.haroon.service.EmailService;

@RestController
@RequestMapping("/api")
public class EmailController {
	
	    @Autowired
	    private EmailService emailService;

	    @PostMapping("/send-email")
	    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) {
	        emailService.sendSimpleEmail(emailDTO.getFrom(), emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getMessage());
	        return ResponseEntity.ok("Email sent successfully!");
	    }

}
