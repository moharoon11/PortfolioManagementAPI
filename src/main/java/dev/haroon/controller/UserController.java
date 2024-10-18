package dev.haroon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.LoginRequest;
import dev.haroon.dto.UserDTO;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(
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
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.loginUser(loginRequest.getUserId(), loginRequest.getEmail(), loginRequest.getPassword());
        
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } 
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestPart("userDTO") UserDTO userDTO,
                                                  @RequestPart(value = "userProfile1", required = false) MultipartFile profile1,
                                                  @RequestPart(value = "userProfile2", required = false) MultipartFile profile2,
                                                  @RequestPart(value = "userProfile3", required = false) MultipartFile profile3,
                                                  @RequestPart(value = "resume", required = false) MultipartFile resume) throws IOException {
        userService.updateUser(userDTO, profile1, profile2, profile3, resume);
        return ResponseEntity.ok(new ApiResponse("User updated successfully", true));
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
