package com.example.Validation.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.Validation.models.UserModel;

@Component
public class UserRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public int save_user(UserModel user) {
	    return jdbcTemplate.update("INSERT INTO user_model (username, mobile, address, email, password, activated, role) VALUES(?,?,?,?,?,?,?)",
	        new Object[] { user.getUsername(), user.getMobile(), user.getAddress(), user.getEmail(), user.getPassword(), user.getActivated(), user.getRole()});
	  }
	public List<UserModel> findByEmail(String email) {
	    return jdbcTemplate.query("SELECT * from user_model WHERE email=?",
	        BeanPropertyRowMapper.newInstance(UserModel.class), email);
	  }
	public List<UserModel> findByUsername(String uname) {
	    return jdbcTemplate.query("SELECT * from user_model WHERE username=?",
	        BeanPropertyRowMapper.newInstance(UserModel.class), uname);
	  }
	public List<UserModel> findAll() {
		return jdbcTemplate.query("SELECT * from user_model",
		        BeanPropertyRowMapper.newInstance(UserModel.class));
	}
	public void activateAccount(String email) {
		jdbcTemplate.update("UPDATE user_model SET activated = true WHERE email = ?",email);
		
	}
	public void updateAccount(UserModel user) {
		jdbcTemplate.update("UPDATE user_model SET username=?, mobile=?, address=? where email=?",new Object[] {user.getUsername(), user.getMobile(), user.getAddress(), user.getEmail()});
	}
	
	public void deleteAccount(String email) {
		jdbcTemplate.update("DELETE from user_model WHERE email=?",email);
	}
}
