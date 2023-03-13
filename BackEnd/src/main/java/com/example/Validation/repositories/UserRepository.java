package com.example.Validation.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.Validation.models.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	User findByEmail(String email);
	Long deleteByEmail(String email);
	User findByUsername(String username);
}
