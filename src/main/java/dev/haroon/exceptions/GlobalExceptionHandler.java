package dev.haroon.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.haroon.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiResponse> noResourceFoundExceptionHandler(NoResourceFoundException ex) {
		return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false), HttpStatus.BAD_REQUEST);
	}
	

	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> handleIOException(IOException ex) {
		return new ResponseEntity(new ApiResponse(ex.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}