package com.flashcard.users.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flashcard.users.dtos.Credentials;
import com.flashcard.users.dtos.Principal;
import com.flashcard.users.entities.User;
import com.flashcard.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/auth")
	public Principal auth(HttpServletRequest req, HttpServletResponse resp, 
			@RequestBody(required = false) Credentials cred) throws Throwable {
		
		Principal principal = null;
		
		if(cred != null) {
			principal = userService.login(cred);
		}
		else {
			String token = req.getHeader("authorization");
			if(token == null || token.equals("")) throw new RuntimeException("Missing Fields");
			
			principal = (Principal) req.getAttribute("principal");
			
			principal = userService.auth(principal);
		}
		
		return principal;
		
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAll();
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/user")
	public Principal createUser(@RequestBody User user) {
		
		Principal principal = userService.createUser(user);
		
		return principal;
	}
	
}
