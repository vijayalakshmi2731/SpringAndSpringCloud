package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	// @Autowired -- Normal
	private UserDaoService service;
	
	
	 //Constructor autowire
	public UserResource(UserDaoService service) {
		this.service = service;
	}

	// Get all users
	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return service.findAll();
	}
	
	@GetMapping("/users/{userId}")
	public User retriveparticularUser(@PathVariable Integer userId) {
		User singleUser = service.findParticularUser(userId);
		if(singleUser == null) {
			throw new UserNotFoundException("UserNotFound for id:" + userId);
		}
		return singleUser;
		 
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{userId}")
	public void deleteParticularUser(@PathVariable Integer userId) {
		boolean flag = service.deleteParticularUser(userId);
		if(!flag) {
			throw new UserNotFoundException("Userid " + userId + " NotFound for deletion");
		}
 
	}
}
