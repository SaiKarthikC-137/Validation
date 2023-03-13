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
import com.example.Validation.models.User;
import com.example.Validation.repositories.UserRepository;
import com.example.Validation.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	public boolean validateEmail(String email) {
		Pattern pattern=Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
		Matcher matcher=pattern.matcher(email);
		return matcher.matches();
	}
	@CrossOrigin
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User data) throws EmailValidationException, DuplicateEmailException, DuplicateUsernameException{
		if(!validateEmail(data.getEmail()))
			throw new EmailValidationException();
		if(userService.findByEmail(data.getEmail()) != null)
			throw new DuplicateEmailException();
		if(userService.findByUsername(data.getUsername())!=null)
			throw new DuplicateUsernameException();
		userService.saveUser(data);
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/findall")
	public ResponseEntity<?> getAll() {
		List<User> users=userService.findAll();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/getuserbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		return new ResponseEntity<>(user.getRole(),HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/activate")
	public ResponseEntity<?> activateAccount(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		user.setActivated(true);
		userService.saveUser(user);
		return new ResponseEntity<>("activated",HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/delete")
	public ResponseEntity<?> deleteAccount(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		userService.deleteByEmail(email);
		return new ResponseEntity<>("deleted",HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User data) throws UserNotFoundException, UserNotActivatedException, IncorrectPasswordException {
		User user=userService.findByEmail(data.getEmail());
		if(user==null) {
			throw new UserNotFoundException();
		}
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
	public ResponseEntity<?> updateUser(@RequestBody User data) throws UserNotFoundException {
		User user=userService.findByEmail(data.getEmail());
		if(user==null) {
			throw new UserNotFoundException();
		}
		data.setUserid(user.getUserid());
		data.setPassword(user.getPassword());
		data.setRole(user.getRole());
		data.setActivated(user.getActivated());
		userService.saveUser(data);
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	

} 
