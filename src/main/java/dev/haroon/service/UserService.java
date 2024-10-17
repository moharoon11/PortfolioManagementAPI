package dev.haroon.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dev.haroon.dto.ImageResponseDTO;
import dev.haroon.dto.UserDTO;
import dev.haroon.exceptions.NoResourceFoundException;

public interface UserService {
	
	public Integer registerUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException;
	
	public boolean loginUser(UserDTO userDTO) throws NoResourceFoundException;
	
	 public void updateUser(UserDTO userDTO, MultipartFile profile1, MultipartFile profile2, MultipartFile profile3, MultipartFile resume) throws IOException, NoResourceFoundException;
	
	public boolean deleteUser(UserDTO userDTO) throws NoResourceFoundException;

	List<ImageResponseDTO> getUserProfileImages(Integer userId) throws NoResourceFoundException;

	ImageResponseDTO getUserResume(Integer userId) throws NoResourceFoundException;

}
