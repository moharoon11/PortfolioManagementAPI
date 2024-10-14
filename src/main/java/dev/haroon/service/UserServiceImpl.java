package dev.haroon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.haroon.dto.UserDTO;
import dev.haroon.entities.User;
import dev.haroon.exceptions.NoResourceFoundException;
import dev.haroon.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	

	@Autowired
	private UserRepo userRepo;
	
	@Override  
	public Integer registerUser(UserDTO userDTO) {
		User user = dtoToUser(userDTO);
		User storedUser = userRepo.save(user);
		return storedUser.getUserId();
	}

	@Override
	public Integer loginUser(UserDTO userDTO) throws NoResourceFoundException {
		User user = userRepo.findById(userDTO.getUserId()).orElseThrow(() -> new NoResourceFoundException("User Not Found!"));
		return user.getUserId();
 	}

	@Override
	public Integer updateUser(UserDTO userDTO) throws NoResourceFoundException{
		User user = userRepo.findById(userDTO.getUserId()).orElseThrow(() -> new NoResourceFoundException("User Not Found!"));
		user = dtoToUser(userDTO);
		user = userRepo.save(user);
		return user.getUserId();
	}

	@Override
	public boolean deleteUser(UserDTO userDTO) {
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

}
