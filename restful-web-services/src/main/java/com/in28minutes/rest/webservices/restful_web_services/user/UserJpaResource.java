package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserRepository userRepository;
	private PostRepository postRepository;
	
	//Constructor autowire
	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@GetMapping("/jpa/users")
	public List<User> retriveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{userId}")
	public EntityModel<User> retriveparticularUser(@PathVariable Integer userId) {
		Optional<User> singleUser = userRepository.findById(userId);
		if(singleUser.isEmpty()) {
			throw new UserNotFoundException("UserNotFound for id:" + userId);
		}
		
		EntityModel<User> entityModel = EntityModel.of(singleUser.get());
		// the below code is to use hateoas to add link to the response without modifying the actual bean/entity
		// methodOn is from the specific pkg so we need to import using * / specific method
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retriveAllUsers());
		entityModel.add(link.withRel("all-users"));

		return entityModel;
		 
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{userId}")
	public void deleteParticularUser(@PathVariable Integer userId) {
		 userRepository.deleteById(userId);
	}
	
	@GetMapping("/jpa/users/{userId}/posts")
	public List<Post> retrivePostsForUser(@PathVariable int userId) {
		Optional<User> singleUser = userRepository.findById(userId);
		if(singleUser.isEmpty()) {
			throw new UserNotFoundException("UserNotFound for id:" + userId);
		}
		return singleUser.get().getPosts();	
	}
	
	@GetMapping("/jpa/users/{userId}/posts/{postId}")
	public Post retriveSinglePostsForUser(@PathVariable int postId) {
		Optional<Post> singlePost = postRepository.findById(postId);
		if(singlePost.isEmpty()) {
			throw new UserNotFoundException("PostNotFound for id:" + postId);
		}
		return singlePost.get();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("UserNotFound for id:" + id);
		}
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedPost.getId())
						.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
