package com.example.Validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> sqlException(Exception e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<?> incorrectPasswordException() {
		return new ResponseEntity<>("Password is incorrect",HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotActivatedException.class)
	public ResponseEntity<?> activationException() {
		return new ResponseEntity<>("User is not activated",HttpStatus.BAD_REQUEST);
	}
	
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
