package dev.haroon.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoResourceFoundException extends RuntimeException {

	
	
	private String message;
	

	public NoResourceFoundException(String message) {
		super(message);
		this.message = message;
	}
	
    public NoResourceFoundException() {
    	
    }
	
}