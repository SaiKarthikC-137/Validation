package com.example.Validation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
		response.put("message", ErrorMessages.IncorrectPassword);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotActivatedException.class)
	public ResponseEntity<?> activationException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.UserNotActivated);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<?> emailException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.DuplicateEmail);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailValidationException.class)
	public ResponseEntity<?> validationExcpetion() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.InvalidEmail);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public ResponseEntity<?> usernameException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.DuplicateUsername);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> usernotfoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.UserNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> requiredFieldMissingException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.RequiredFieldMissing);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> noRequestBodyFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.NoRequestBody);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingAddressException.class)
	public ResponseEntity<?> noAddressFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.AddressNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingEmailException.class)
	public ResponseEntity<?> noEmailFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.EmailNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingMobileException.class)
	public ResponseEntity<?> noMobileFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.MobileNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingPasswordException.class)
	public ResponseEntity<?> noPasswordFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.PasswordNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingUsernameException.class)
	public ResponseEntity<?> noUsernameFoundException() {
		Map<String, Object> response=new HashMap<>();
		response.put("message", ErrorMessages.UsernameNotFound);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> otherExceptions(Exception e) {
		Map<String, Object> response=new HashMap<>();
		response.put("message", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

}
