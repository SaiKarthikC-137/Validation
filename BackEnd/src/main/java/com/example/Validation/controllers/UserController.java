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
	public void registerUser(@RequestBody UserModel data){
		if(!validateEmail(data.getEmail()))
			return;
		userRepository.save_user(data);
	}
	
	@CrossOrigin
	@GetMapping("/findall")
	public ResponseEntity<?> getAll() {
		List<UserModel> users=userRepository.findAll();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/getuserbyemail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
		List<UserModel> users=userRepository.findByEmail(email);
		System.out.println(users.get(0).getUsername());
		return new ResponseEntity<>(users.get(0),HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(@RequestParam String email) {
		List<UserModel> users=userRepository.findByEmail(email);
		return new ResponseEntity<>(users.get(0).getRole(),HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping("/activate")
	public ResponseEntity<?> activateAccount(@RequestParam String username) {
		userRepository.activateAccount(username);
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/delete")
	public ResponseEntity<?> deleteAccount(@RequestParam String email) {
		userRepository.deleteAccount(email);
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserModel data) {
		boolean flag=false;
		 List<UserModel> users=userRepository.findByEmail(data.getEmail());
		 if(users.size()>0) {
			 if(users.get(0).getPassword().equals(data.getPassword()) && users.get(0).getActivated()) {
				 flag=true;
			 }
		 }
		 return new ResponseEntity<>(flag,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PostMapping("/updateuser")
	public ResponseEntity<?> updateUser(@RequestBody UserModel data) {
		userRepository.updateAccount(data);
		return new ResponseEntity<>(true,HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping("/finduser")
	public ResponseEntity<?> findUsers(@RequestBody UserModel data) {
		boolean found=false;
		if(userRepository.findByEmail(data.getEmail()).size()!=0) {
			found=true;
		} 
		return new ResponseEntity<>(found,HttpStatus.CREATED);
	}
	

} 
