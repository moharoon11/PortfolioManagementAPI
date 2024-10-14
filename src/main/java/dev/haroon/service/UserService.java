package dev.haroon.service;

import dev.haroon.dto.UserDTO;
import dev.haroon.exceptions.NoResourceFoundException;

public interface UserService {
	
	public Integer registerUser(UserDTO userDTO);
	
	public Integer loginUser(UserDTO userDTO) throws NoResourceFoundException;
	
	public Integer updateUser(UserDTO userDTO) throws NoResourceFoundException;
	
	public boolean deleteUser(UserDTO userDTO) throws NoResourceFoundException;

}
