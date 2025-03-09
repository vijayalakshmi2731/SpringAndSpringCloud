package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "USER_DETAILS")
public class User {
		
	// For jpa default constructor is need for Entity
	protected User() {
		
	}
	
	@Id
	@GeneratedValue
	@JsonProperty("User_id")
	private Integer id;
	
	@Past(message = "Birth date should be past date")
	@JsonProperty("Birth_date")
	private LocalDate birthdate;
	
	@Size(min=2, message = "Name should have minimum length of 2")
	@JsonProperty("User_name")
	private String name;
	
	@OneToMany(mappedBy = "user") // mapped means owns the relationship
	@JsonIgnore
	private List<Post> posts;

	public User(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}	

}
