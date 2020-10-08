package com.flashcard.users.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.flashcard.users.exceptions.BadRequest;
import com.flashcard.users.exceptions.ServerError;
import com.flashcard.users.services.UserService;
import com.flashcard.users.utils.validators.PrincipalValidator;
import com.flashcard.users.utils.validators.UserValidator;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/auth")
	public Principal auth(HttpServletRequest req, HttpServletResponse resp) {
		
		Principal principal = (Principal) req.getAttribute("principal");
		PrincipalValidator.validate(principal);
		
		principal = userService.auth(principal);
		
		return principal;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/login")
	public Principal login(@RequestBody Credentials cred) {
		Principal principal = null;
		
		try {
			logger.debug("Credentials: " + cred.toString());
			principal = userService.login(cred);
			
		} catch(BadRequest e) {
			logger.debug(e.getMessage());
			throw e;
		} catch(ServerError e) {
			logger.debug(e.getMessage());
			throw e;
		}
		
		return principal;
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<User> getAllUsers(HttpServletRequest req, HttpServletResponse resp) {
		Principal principal = (Principal) req.getAttribute("principal");
		PrincipalValidator.validate(principal);
		
		return userService.getAll();
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/user")
	public Principal createUser(@RequestBody User user) {
		
		UserValidator.validate(user);
		Principal principal = userService.createUser(user);
		
		return principal;
	}
	
}
