package com.flashcard.users.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.flashcard.users.repositories.UserRepository;
import com.flashcard.users.services.UserService;

@WebMvcTest(UserController.class)
public class TestUserControllerAuth {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepo;
	
	@Test
	public void testAuthWithCredentials() {
		
	}
	
}
