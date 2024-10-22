package dev.haroon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.LoginRequest;
import dev.haroon.dto.UserDTO;
import dev.haroon.entities.User;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.service.UserService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
    	@Valid
        @RequestPart("userDTO") UserDTO userDTO,
        @RequestPart("userProfile1") MultipartFile profile1,
        @RequestPart("userProfile2") MultipartFile profile2,
        @RequestPart("userProfile3") MultipartFile profile3,
        @RequestPart("resume") MultipartFile resume
    ) throws IOException {
        ApiResponse response = userService.registerUser(userDTO, profile1, profile2, profile3, resume);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest ) {
    	
    	
        boolean isAuthenticated = userService.loginUser(loginRequest.getUserId(), loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } 
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    
    
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
    	return ResponseEntity.ok(userService.getUserById(userId));
    }


    



    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(
        @RequestPart("userDTO") String userDTOJson, // Accept it as String
        @RequestPart(value = "userProfile1", required = false) MultipartFile profile1,
        @RequestPart(value = "userProfile2", required = false) MultipartFile profile2,
        @RequestPart(value = "userProfile3", required = false) MultipartFile profile3,
        @RequestPart(value = "resume", required = false) MultipartFile resume) throws IOException {

        // Parse userDTO from JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.readValue(userDTOJson, UserDTO.class);

        // Debug logging to verify data received
        System.out.println("UserDTO: " + userDTO);
        System.out.println("Profile1: " + (profile1 != null ? profile1.getOriginalFilename() : "null"));
        System.out.println("Profile2: " + (profile2 != null ? profile2.getOriginalFilename() : "null"));
        System.out.println("Profile3: " + (profile3 != null ? profile3.getOriginalFilename() : "null"));
        System.out.println("Resume: " + (resume != null ? resume.getOriginalFilename() : "null"));

        // Continue processing
        userService.updateUser(userDTO, profile1, profile2, profile3, resume);
        return ResponseEntity.ok(new ApiResponse("User updated successfully", true));
    }
    
    @PutMapping("/update/info")
    public ResponseEntity<ApiResponse> updateUserFields(
            @RequestBody UserDTO userDTO) {
        // Process userDTO (no files)
        userService.updateUserFields(userDTO);
        return ResponseEntity.ok(new ApiResponse("User fields updated successfully", true));
    }

    @PutMapping("/update/userImage1/{userId}")
    public ResponseEntity<ApiResponse> updateUserImage1(
            @PathVariable Integer userId,
            @RequestPart(value = "userImage1", required = false) MultipartFile userImage1) throws IOException {
        
        // Process file uploads
        String message = userService.updateProfileImage(userId, userImage1, "image1");
        return ResponseEntity.ok(new ApiResponse(message , true));
    }
    
    @PutMapping("/update/userImage2/{userId}")
    public ResponseEntity<ApiResponse> updateUserImage2(
            @PathVariable Integer userId,
            @RequestPart(value = "userImage2", required = false) MultipartFile userImage2
       ) throws IOException {
        
        // Process file uploads
        String message = userService.updateProfileImage(userId, userImage2, "image2");
        return ResponseEntity.ok(new ApiResponse(message , true));
    }
    
    @PutMapping("/update/userImage3/{userId}")
    public ResponseEntity<ApiResponse> updateUserImage3(
            @PathVariable Integer userId,
            @RequestPart(value = "userImage3", required = false) MultipartFile userImage3
       ) throws IOException {
        
        // Process file uploads
        String message = userService.updateProfileImage(userId, userImage3, "image3");
        return ResponseEntity.ok(new ApiResponse(message , true));
    }
    
    @PutMapping("/update/resume/{userId}")
    public ResponseEntity<ApiResponse> updateResume(
            @PathVariable Integer userId,
            @RequestPart(value = "resume", required = false) MultipartFile resume) throws IOException {
        
        // Process file uploads
        String message = userService.updateProfileImage(userId, resume, "resume");
        return ResponseEntity.ok(new ApiResponse(message , true));
    }



    

    // Delete User
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteUser(userDTO);
        return ResponseEntity.ok(new ApiResponse("User Deleted successfully", true));
    }
    
    @GetMapping("/images/{userId}")
    public ResponseEntity<List<ImageResponseDTO>> getProfileImages(@PathVariable Integer userId) {
        List<ImageResponseDTO> images = userService.getUserProfileImages(userId);
        
        // Check if the list is empty
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(images); // 204 No Content
        }

        return ResponseEntity.ok(images); // 200 OK
    }
    
    @GetMapping("/images/{userId}/image1")
    public ResponseEntity<ImageResponseDTO> getProfileImage1(@PathVariable("userId") Integer userId) throws NoResourceFoundException, IOException {
    	ImageResponseDTO imageResponseDTO = userService.loadImage(userId, "image1");
    	return ResponseEntity.ok(imageResponseDTO);
    }
    
    @GetMapping("/images/{userId}/image2")
    public ResponseEntity<ImageResponseDTO> getProfileImage2(@PathVariable("userId") Integer userId) throws NoResourceFoundException, IOException {
    	ImageResponseDTO imageResponseDTO = userService.loadImage(userId, "image2");
    	return ResponseEntity.ok(imageResponseDTO);
    }
    
    @GetMapping("/images/{userId}/image3")
    public ResponseEntity<ImageResponseDTO> getProfileImage3(@PathVariable("userId") Integer userId) throws NoResourceFoundException, IOException {
    	ImageResponseDTO imageResponseDTO = userService.loadImage(userId, "image3");
    	return ResponseEntity.ok(imageResponseDTO);
    }


 // Get Resume by User ID
    @GetMapping("/resume/{userId}")
    public ResponseEntity<ImageResponseDTO> getResume(@PathVariable Integer userId) {
        ImageResponseDTO resume = userService.getUserResume(userId);

        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }

        return ResponseEntity.ok(resume); // Return the entire DTO with image data and metadata
    }


}
