package com.example.Validation.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Validation.exceptions.DuplicateEmailException;
import com.example.Validation.exceptions.DuplicateUsernameException;
import com.example.Validation.exceptions.EmailValidationException;
import com.example.Validation.exceptions.IncorrectPasswordException;
import com.example.Validation.exceptions.UserNotActivatedException;
import com.example.Validation.exceptions.UserNotFoundException;
import com.example.Validation.models.UserModel;
import com.example.Validation.repositories.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	public boolean validateEmail(String email) {
		Pattern pattern=Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
		Matcher matcher=pattern.matcher(email);
		return matcher.matches();
	}
	@CrossOrigin
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserModel data) throws EmailValidationException, DuplicateEmailException, DuplicateUsernameException{
		if(!validateEmail(data.getEmail()))
			throw new EmailValidationException();
		if(!userRepository.findByEmail(data.getEmail()).isEmpty())
			throw new DuplicateEmailException();
		if(!userRepository.findByUsername(data.getUsername()).isEmpty())
			throw new DuplicateUsernameException();
		userRepository.save_user(data);
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/findall")
	public ResponseEntity<?> getAll() {
		List<UserModel> users=userRepository.findAll();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/getuserbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) throws UserNotFoundException {
		List<UserModel> users=userRepository.findByEmail(email);
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		return new ResponseEntity<>(users.get(0),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(@RequestParam String email) throws UserNotFoundException {
		List<UserModel> users=userRepository.findByEmail(email);
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		return new ResponseEntity<>(users.get(0).getRole(),HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/activate")
	public ResponseEntity<?> activateAccount(@RequestParam String email) throws UserNotFoundException {
		List<UserModel> users=userRepository.findByEmail(email);
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		userRepository.activateAccount(email);
		return new ResponseEntity<>("activated",HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/delete")
	public ResponseEntity<?> deleteAccount(@RequestParam String email) throws UserNotFoundException {
		List<UserModel> users=userRepository.findByEmail(email);
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		userRepository.deleteAccount(email);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserModel data) throws UserNotFoundException, UserNotActivatedException, IncorrectPasswordException {
		List<UserModel> users=userRepository.findByEmail(data.getEmail());
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		UserModel user=users.get(0);
		if(!user.getActivated()) {
			throw new UserNotActivatedException();
		}
		if(!user.getPassword().equals(data.getPassword())) {
			throw new IncorrectPasswordException();
		}
		return new ResponseEntity<>("logged in",HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/updateuser")
	public ResponseEntity<?> updateUser(@RequestBody UserModel data) throws UserNotFoundException {
		List<UserModel> users=userRepository.findByEmail(data.getEmail());
		if(users.isEmpty()) {
			throw new UserNotFoundException();
		}
		userRepository.updateAccount(data);
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	

} 
