package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.exception.UserAlreadyExistsException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean createNewUser(AuthenticationRequest registeringUser) throws Exception {
		
		Optional<User> isAlreadyRegistered = userRepo.findByUsername(registeringUser.getUsername());
		
		if (isAlreadyRegistered.isPresent()) {
			throw new UserAlreadyExistsException(registeringUser.getUsername());
		}
		
		User newUser = new User();
		newUser.setUsername(registeringUser.getUsername());
		newUser.setPassword(passwordEncoder.encode(registeringUser.getPassword()));
		newUser.setEnabled(true);
		newUser.setRole(Role.valueOf("ROLE_USER"));
		
		userRepo.save(newUser);
		return true;
		
	}

	

}
