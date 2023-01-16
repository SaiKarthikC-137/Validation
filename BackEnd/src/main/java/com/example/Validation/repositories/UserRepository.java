package com.example.Validation.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.Validation.models.UserModel;

@Component
public class UserRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public int save_user(UserModel user) {
	    return jdbcTemplate.update("INSERT INTO user_model VALUES(?,?)",
	        new Object[] { user.getEmail(), user.getPassword()});
	  }
	public List<UserModel> findByEmail(String email) {
	    return jdbcTemplate.query("SELECT * from user_model WHERE email=?",
	        BeanPropertyRowMapper.newInstance(UserModel.class), email);
	  }
}
