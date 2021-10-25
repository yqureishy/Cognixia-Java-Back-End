package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	
@Autowired
UserRepository userRepo;

@Autowired
UserService userService;
	
@GetMapping("/users")
public List<User> getAllUsers(){
	
	return userRepo.findAll();
	
}

@PostMapping("/add/user")
public ResponseEntity<?> addUser(@RequestBody AuthenticationRequest registeringUser) throws Exception {
	
	userService.createNewUser(registeringUser);
	
	return ResponseEntity.ok(registeringUser.getUsername());
	
	
	
}

}
