package com.flashcard.users.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.flashcard.users.TestUtils;
import com.flashcard.users.dtos.Principal;
import com.flashcard.users.entities.User;
import com.flashcard.users.repositories.UserRepository;
import com.flashcard.users.services.UserService;

@WebMvcTest(UserController.class)
public class TestUserCreation {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepo;
	
	@Test
	public void testUserServiceMocking() {
		User user = new User();
		user.setUsername("testusername");
		user.setPasswd("test");
		Principal pr = new Principal(user);
		when(userService.createUser(user)).thenReturn(pr);
		
		assertEquals(pr, this.userService.createUser(user));
	}
	
	@Test
	public void testUserCreation() throws Exception {
		User user = new User();
		user.setUsername("testusername");
		user.setPasswd("test");
		Principal pr = new Principal(user);
		when(userService.createUser(user)).thenReturn(pr);
		
		String requestJson = TestUtils.objToJson(user);
		
	    mvc.perform(
					post("/users/user", user)
					.contentType(new MediaType(
							MediaType.APPLICATION_JSON.getType(),
							MediaType.APPLICATION_JSON.getSubtype(),
							Charset.forName("utf8"))
					)
					.content(requestJson)
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string(containsString("\"username\":\"testusername\"")));
	}
	
}
