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
		newUser.setEmail(registeringUser.getEmail());
		newUser.setRole(Role.valueOf("ROLE_USER"));
		
		userRepo.save(newUser);
		return true;
		
	}
	
public User updateUsername(long id, String username) throws Exception {
	Optional <User> found = userRepo.findById(id);
	
	if(found.isPresent()) {
		User toUpdate = found.get();
		
		toUpdate.setUsername(username);
		
		User updated = userRepo.save(toUpdate);
		
		return updated;
	}
	
	throw new Exception("Username with id: " + id + " can not be found.");
}

public User updateEmail(long id, String email) throws Exception {
	Optional <User> found = userRepo.findById(id);
	
	if(found.isPresent()) {
		User toUpdate = found.get();
		
		toUpdate.setEmail(email);
		
		User updated = userRepo.save(toUpdate);
		
		return updated;
	}
	
	throw new Exception("Username with id: " + id + " can not be found.");
}

	

}
