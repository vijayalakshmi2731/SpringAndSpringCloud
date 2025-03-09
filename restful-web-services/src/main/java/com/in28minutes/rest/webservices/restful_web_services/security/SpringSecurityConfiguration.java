package com.in28minutes.rest.webservices.restful_web_services.security;


import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// In spring security, all req should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		// If a req is not authenticated a web page is shown
		http.httpBasic(withDefaults());
		// CSRF -> POST, PUT (validsation needs to be diabled)
		
		http.csrf().disable();
		
		return http.build();
	}
	
}
