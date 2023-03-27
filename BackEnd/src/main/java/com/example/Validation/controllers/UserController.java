package com.example.Validation.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.example.Validation.exceptions.MissingAddressException;
import com.example.Validation.exceptions.MissingEmailException;
import com.example.Validation.exceptions.MissingMobileException;
import com.example.Validation.exceptions.MissingPasswordException;
import com.example.Validation.exceptions.MissingUsernameException;
import com.example.Validation.exceptions.UserNotActivatedException;
import com.example.Validation.exceptions.UserNotFoundException;
import com.example.Validation.models.User;
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
	public ResponseEntity<?> registerUser(@RequestBody User data) throws EmailValidationException, DuplicateEmailException, DuplicateUsernameException, MissingEmailException, MissingPasswordException, MissingUsernameException, MissingMobileException, MissingAddressException{
		if(data.getEmail()==null) {
			throw new MissingEmailException();
		}
		if(data.getPassword()==null) {
			throw new MissingPasswordException();
		}
		if(data.getUsername()==null) {
			throw new MissingUsernameException();
		}
		if(data.getMobile()==null) {
			throw new MissingMobileException();
		}
		if(data.getAddress()==null) {
			throw new MissingAddressException();
		}
		if(!validateEmail(data.getEmail()))
			throw new EmailValidationException();
		if(userService.findByEmail(data.getEmail()) != null)
			throw new DuplicateEmailException();
		if(userService.findByUsername(data.getUsername())!=null)
			throw new DuplicateUsernameException();
		User user=userService.saveUser(data);
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Registered the user "+data.getUsername());
		response.put("user", user);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/findall")
	public ResponseEntity<?> getAll() {
		List<User> users=userService.findAll();
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Fetched users");
		response.put("users", users);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/getuserbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Fetched user with the email "+email);
		response.put("user", user);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Fetched role of the user "+user.getUsername());
		response.put("role", user.getRole());
		return new ResponseEntity<>(response,HttpStatus.OK);
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
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Activated the user "+user.getUsername());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/delete")
	public ResponseEntity<?> deleteAccount(@RequestParam String email) throws UserNotFoundException {
		User user=userService.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException();
		}
		userService.deleteByEmail(email);
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Deleted the user "+user.getUsername());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User data) throws UserNotFoundException, UserNotActivatedException, IncorrectPasswordException, MissingEmailException, MissingPasswordException {
		if(data.getEmail()==null) {
			throw new MissingEmailException();
		}
		if(data.getPassword()==null) {
			throw new MissingPasswordException();
		}
		User user=userService.findByEmail(data.getEmail());
		if(user==null) {
			throw new UserNotFoundException();
		}
		if(!user.getPassword().equals(data.getPassword())) {
			throw new IncorrectPasswordException();
		}
		if(!user.getActivated()) {
			throw new UserNotActivatedException();
		}
		Map<String, Object> response=new HashMap<>();
		response.put("message", user.getUsername()+" successfully logged in");
		return new ResponseEntity<>(response,HttpStatus.OK);
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
		User updatedUser=userService.saveUser(data);
		Map<String, Object> response=new HashMap<>();
		response.put("message", "Updated the user "+user.getUsername());
		response.put("user", updatedUser);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

} 
