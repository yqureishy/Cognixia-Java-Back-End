package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService UserDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {

		return userRepo.findAll();

	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable long id) throws ResourceNotFoundException {
		Optional <User> found = userRepo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		throw new ResourceNotFoundException("User with id: " + id + " does not exist.");
	}

	@PostMapping("/add/user")
	public ResponseEntity<?> addUser(@RequestBody AuthenticationRequest registeringUser) throws Exception {

		userService.createNewUser(registeringUser);

		return ResponseEntity.ok(registeringUser.getUsername() + " has been added!");

	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = UserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}
	
	@DeleteMapping("delete/user/{id}")
	public User deleteUser(@PathVariable long id) throws ResourceNotFoundException {
		if(userRepo.existsById(id)) {
			User deleted = userRepo.getById(id);
			userRepo.deleteById(id);
			
			return deleted;
		}
		
		throw new ResourceNotFoundException("User with id:" + id + " does not exist, therefore it could not be deleted.");
	}
	
	
	// UPDATE USERNAME
	
	@PatchMapping("update/user/username")
	public ResponseEntity<?> updateUsername(@RequestBody Map<String, String> updateInfo) throws Exception {
		
		long id = Integer.parseInt(updateInfo.get("id"));
		String username = updateInfo.get("username");
		
		if(userRepo.existsById(id)) {
			String oldUsername = userRepo.getById(id).getUsername();
			
			userRepo.updateUsername(username, id);
			
			return ResponseEntity.status(200)
					.body("Old Username: " + oldUsername + ", New Username: " + username);
		}
		
		throw new ResourceNotFoundException("Username already exists, please choose another username.");
		
	}
	
	// UPDATE EMAIL
	
	@PatchMapping("update/user/email")
	public ResponseEntity<?> updateEmail(@RequestBody Map<String, String> updateInfo) throws Exception {
		
		long id = Integer.parseInt(updateInfo.get("id"));
		String email = updateInfo.get("email");
		
		if(userRepo.existsById(id)) {
			String oldEmail = userRepo.getById(id).getEmail();
			
			userRepo.updateEmail(email, id);
			
			return ResponseEntity.status(200)
					.body("Old Email: " + oldEmail + ", New Email: " + email);
		}
		
		throw new ResourceNotFoundException("Email already exists, please choose another email.");
		
	}
	
	// UPDATE PASSWORD
	
		@PatchMapping("update/user/password")
		public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> updateInfo) throws Exception {
			
			long id = Integer.parseInt(updateInfo.get("id"));
			String password = updateInfo.get("password");
			
			if(userRepo.existsById(id)) {
				String oldPassword = userRepo.getById(id).getPassword();
				
				userRepo.updatePassword(passwordEncoder.encode(password), id);
				
				return ResponseEntity.status(200)
						.body("Your New Password is: " + password);
			}
			
			throw new Exception();
			
		}

}
