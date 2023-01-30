package com.example.Validation.models;


public class UserModel {
	private Integer userid;
	private String username;
	private String mobile;
	private String address;
	private String email;
	private String password;
	private String role;
	private Boolean activated;
	
	public UserModel() {
	}

	public UserModel(Integer userid, String username, String mobile, String address, String email, String password, String role,
			Boolean activated) {
		this.userid = userid;
		this.username = username;
		this.mobile = mobile;
		this.address = address;
		this.email = email;
		this.password = password;
		this.role=role;
		this.activated = activated;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public UserModel(String username, String mobile, String address, String email, String password, Boolean activated) {
		this.username = username;
		this.mobile = mobile;
		this.address = address;
		this.email = email;
		this.password = password;
		this.activated = activated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
}
