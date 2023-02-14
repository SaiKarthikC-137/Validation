package com.example.Validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<?> emailException() {
		return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailValidationException.class)
	public ResponseEntity<?> validationExcpetion() {
		return new ResponseEntity<>("Email not valid",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<?> usernameException() {
		return new ResponseEntity<>("Username already exists",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> usernotfoundException() {
		return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
	}

}
