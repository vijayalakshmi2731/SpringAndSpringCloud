package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	// First static user array list
	private static List<User> users = new ArrayList<>();
	
	public static int userId = 0;
	
	static {
		users.add(new User(++userId, "Lingkesh", LocalDate.now().minusYears(27)));
		users.add(new User(++userId, "Viji", LocalDate.now().minusYears(24)));
		users.add(new User(++userId, "Adam", LocalDate.now().minusYears(30)));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User findParticularUser(Integer id) {
		
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);				
//		return users.stream()
//				.filter(user -> user.getId().equals(id)).findFirst().orElse(null);
	}
	
	public User save(User user) {
		user.setId(++userId);
		users.add(user);
		return user;
	}

	public boolean deleteParticularUser(Integer userId) {
		Predicate<? super User> predicate = user -> user.getId().equals(userId);
		return users.removeIf(predicate);
		
	}
	
}
