package com.example.Validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> sqlException(Exception e) {
		Map<String, Object> response=new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<?> incorrectPasswordException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Password is incorrect");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotActivatedException.class)
	public ResponseEntity<?> activationException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "User is not activated");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<?> emailException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Email already exists");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailValidationException.class)
	public ResponseEntity<?> validationExcpetion() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Email is not valid");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<?> usernameException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Username already exists");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> usernotfoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", "User not found");
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> otherExceptions(Exception e) {
		Map<String, Object> response=new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
