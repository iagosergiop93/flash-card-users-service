package com.flashcard.users.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flashcard.users.dtos.Credentials;
import com.flashcard.users.dtos.Principal;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/auth")
	public Principal auth(HttpServletRequest req, HttpServletResponse resp, 
			@RequestBody(required = false) Credentials cred) throws Throwable {
		return null;
	}
	
}
