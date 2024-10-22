package dev.haroon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.UserDTO;
import dev.haroon.entities.User;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	

	@Autowired
	private UserRepo userRepo;
	
	

	@Override
	public ApiResponse registerUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException {
	    // Check if user already exists
	    if (userRepo.existsById(userDTO.getUserId())) {
	        return new ApiResponse("User with this ID already exists! Please try creating with a different user ID.", false);
	    }
	    
	    // Convert UserDTO to User entity
	    User user = dtoToUser(userDTO);

	    // Handle profile images and resume upload
	    if (profile1 != null && !profile1.isEmpty()) {
	        user.setUserImage1(profile1.getBytes());
	        user.setUserImage1FileName(profile1.getOriginalFilename());
	        user.setUserImage1Type(profile1.getContentType());
	    }

	    if (profile2 != null && !profile2.isEmpty()) {
	        user.setUserImage2(profile2.getBytes());
	        user.setUserImage2FileName(profile2.getOriginalFilename());
	        user.setUserImage2Type(profile2.getContentType());
	    }

	    if (profile3 != null && !profile3.isEmpty()) {
	        user.setUserImage3(profile3.getBytes());
	        user.setUserImage3FileName(profile3.getOriginalFilename());
	        user.setUserImage3Type(profile3.getContentType());
	    }

	    // Handle resume upload
	    if (resume != null && !resume.isEmpty()) {
	        user.setResume(resume.getBytes());
	        user.setResumeName(resume.getOriginalFilename());
	        user.setResumeType(resume.getContentType());
	    }

	    // Save the user
	    userRepo.save(user);

	    return new ApiResponse("User registered successfully!", true);
	}



	@Override
	public boolean loginUser(Integer userId, String email, String password) throws NoResourceFoundException {
	    // Retrieve user by userId
	    User user = userRepo.findById(userId)
	                        .orElseThrow(() -> new NoResourceFoundException("User Not Found!"));

	    // Check if the provided email and password match the stored values
	    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
	        return true; // Authentication successful
	    } else {
	        throw new NoResourceFoundException("Invalid Credentials"); // Authentication failed
	    }
	}
	
	 @Override
	    public void updateUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException, NoResourceFoundException {
	        User user = userRepo.findById(userDTO.getUserId())
	                            .orElseThrow(() -> new NoResourceFoundException("User Not Found!"));

	        // Update basic user details
	        user.setName(userDTO.getName());
	        user.setEmail(userDTO.getEmail());
	        user.setPassword(userDTO.getPassword());
	        user.setRole(userDTO.getRole());
	        user.setAbout(userDTO.getAbout());

	        // Check and update the files if present
	        if (profile1 != null && !profile1.isEmpty()) {
	            user.setUserImage1(profile1.getBytes());
	            user.setUserImage1FileName(profile1.getOriginalFilename());
	            user.setUserImage1Type(profile1.getContentType());
	        }

	        if (profile2 != null && !profile2.isEmpty()) {
	            user.setUserImage2(profile2.getBytes());
	            user.setUserImage2FileName(profile2.getOriginalFilename());
	            user.setUserImage2Type(profile2.getContentType());
	        }

	        if (profile3 != null && !profile3.isEmpty()) {
	            user.setUserImage3(profile3.getBytes());
	            user.setUserImage3FileName(profile3.getOriginalFilename());
	            user.setUserImage3Type(profile3.getContentType());
	        }

	        if (resume != null && !resume.isEmpty()) {
	            user.setResume(resume.getBytes());
	            user.setResumeName(resume.getOriginalFilename());
	            user.setResumeType(resume.getContentType());
	        }

	        // Save the updated user details to the database
	        userRepo.save(user);
	    }
	 
	 
	 /**
	  * private Integer userId;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	private String about;
	  */
	 
	    @Override
		public String updateUserFields(UserDTO userDTO) throws NoResourceFoundException {
			User user = userRepo.findById(userDTO.getUserId()).get();
			
			if(user == null) {
				throw new NoResourceFoundException("User not found");
			}
			
			user.setName(userDTO.getName());
			user.setEmail(userDTO.getEmail());
			user.setPassword(userDTO.getPassword());
			user.setRole(userDTO.getRole());
			user.setAbout(userDTO.getAbout());
			
			userRepo.save(user);
			return "User updated Sucessfully";
		}
	    
	 
	    @Override
	    public String updateProfileImage(Integer userId, MultipartFile profileImage, String whichProfile) 
	            throws NoResourceFoundException, IOException {
	        
	        // Check if user exists
	        User user = userRepo.findById(userId)
	            .orElseThrow(() -> new NoResourceFoundException("User not found"));

	        // Validate the uploaded file
	        if (profileImage == null || profileImage.isEmpty()) {
	            return "No file provided for update";
	        }
	        
	        // Process the uploaded file based on whichProfile
	        switch (whichProfile) {
	            case "image1":
	                user.setUserImage1(profileImage.getBytes());
	                user.setUserImage1FileName(profileImage.getOriginalFilename());
	                user.setUserImage1Type(profileImage.getContentType());
	                break;
	            case "image2":
	                user.setUserImage2(profileImage.getBytes());
	                user.setUserImage2FileName(profileImage.getOriginalFilename());
	                user.setUserImage2Type(profileImage.getContentType());
	                break;
	            case "image3":
	                user.setUserImage3(profileImage.getBytes());
	                user.setUserImage3FileName(profileImage.getOriginalFilename());
	                user.setUserImage3Type(profileImage.getContentType());
	                break;
	            case "resume":
	                user.setResume(profileImage.getBytes());
	                user.setResumeName(profileImage.getOriginalFilename());
	                user.setResumeType(profileImage.getContentType());
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid profile type specified");
	        }

	        // Save the user only once after all updates
	        userRepo.save(user);
	        return whichProfile + " updated successfully";
	    }



	 
	 @Override
	 public List<ImageResponseDTO> getUserProfileImages(Integer userId) throws NoResourceFoundException {
	     User user = userRepo.findById(userId)
	                         .orElseThrow(() -> new NoResourceFoundException("User Not Found!"));

	     List<ImageResponseDTO> profileImages = new ArrayList<>();
	     
	     // Check for each image and add to the list
	     if (user.getUserImage1() != null) {
	         profileImages.add(new ImageResponseDTO(user.getUserImage1(), user.getUserImage1FileName(), user.getUserImage1Type()));
	     }
	     if (user.getUserImage2() != null) {
	         profileImages.add(new ImageResponseDTO(user.getUserImage2(), user.getUserImage2FileName(), user.getUserImage2Type()));
	     }
	     if (user.getUserImage3() != null) {
	         profileImages.add(new ImageResponseDTO(user.getUserImage3(), user.getUserImage3FileName(), user.getUserImage3Type()));
	     }

	     return profileImages;
	 }

	 @Override
	 public ImageResponseDTO getUserResume(Integer userId) throws NoResourceFoundException {
	     User user = userRepo.findById(userId)
	                         .orElseThrow(() -> new NoResourceFoundException("User Not Found!"));

	     return new ImageResponseDTO(user.getResume(), user.getResumeName(), user.getResumeType());
	 }
	 
	    @Override
		public ImageResponseDTO loadImage(Integer userId, String whichImage) throws NoResourceFoundException, IOException {
	    	 User user = userRepo.findById(userId)
                     .orElseThrow(() -> new NoResourceFoundException("User Not Found!"));
	    	 
	    	 ImageResponseDTO imageResponseDTO = null;
	    	 
	    	 if(whichImage.equals("image1")) {
	    		 imageResponseDTO = new ImageResponseDTO(user.getUserImage1(), user.getUserImage1FileName(), user.getUserImage1Type());
	    	 } else if(whichImage.equals("image2")) {
	    		 imageResponseDTO = new ImageResponseDTO(user.getUserImage2(), user.getUserImage2FileName(), user.getUserImage2Type());
	    	 } else if(whichImage.equals("image3")) {
	    		 imageResponseDTO = new ImageResponseDTO(user.getUserImage3(), user.getUserImage3FileName(), user.getUserImage3Type());
	    	 }
	    	
			return imageResponseDTO;
		}




	@Override
	public boolean deleteUser(UserDTO userDTO) throws NoResourceFoundException {
		User user = userRepo.findById(userDTO.getUserId()).orElseThrow(() ->  new NoResourceFoundException("User Not Found!"));
		userRepo.delete(user);
		return true;
	}
	
	private User dtoToUser(UserDTO userDTO) {
		User user = new User();
		user.setUserId(userDTO.getUserId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());
		user.setAbout(userDTO.getAbout());
		return user;
	}
	
	public UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		userDTO.setAbout(user.getAbout());
		userDTO.setRole(user.getRole());
		return userDTO;
	}



	@Override
	public User getUserById(Integer userId) {
		return userRepo.findById(userId).get();
	}



	



	



	
}
