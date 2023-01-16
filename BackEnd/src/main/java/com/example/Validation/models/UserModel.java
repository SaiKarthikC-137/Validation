package com.example.Validation.models;


public class UserModel {
	private String email;
	private String password;
	
	public UserModel() {
		super();
	}
	public UserModel(String email, String password) {
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String username) {
		this.email = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
