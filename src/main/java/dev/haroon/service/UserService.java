package dev.haroon.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ApiResponse;
import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.UserDTO;
import dev.haroon.exceptions.NoResourceFoundException;


public interface UserService {
	
	public ApiResponse registerUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException;
	
	public boolean loginUser(Integer userId, String email, String password) throws NoResourceFoundException;
	
	 public void updateUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException, NoResourceFoundException;
	
	public boolean deleteUser(UserDTO userDTO) throws NoResourceFoundException;

	List<ImageResponseDTO> getUserProfileImages(Integer userId) throws NoResourceFoundException;

	ImageResponseDTO getUserResume(Integer userId) throws NoResourceFoundException;

}
