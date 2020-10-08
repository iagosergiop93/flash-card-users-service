package com.flashcard.users.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.flashcard.users.TestUtils;
import com.flashcard.users.dtos.Credentials;
import com.flashcard.users.dtos.Principal;
import com.flashcard.users.entities.Role;
import com.flashcard.users.exceptions.BadRequest;
import com.flashcard.users.filters.PrincipalReaderFilter;
import com.flashcard.users.repositories.UserRepository;
import com.flashcard.users.services.UserService;

@WebMvcTest
public class TestUserControllerAuth {
	
	private Logger logger = LoggerFactory.getLogger(TestUserControllerAuth.class);
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MockHttpServletRequest mockreq;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepo;
	
	private Principal createPrincipal() {
		Principal pri = new Principal();
		pri.setId(1);
		pri.setUsername("usertest");
		pri.setRole(new Role(1, "USER"));
		return pri;
	}
	
	@Test
	public void unitTestLogin() throws Throwable {
		Credentials cred = new Credentials("usertest", "pass");
		Principal pri = createPrincipal();
		
		when(userService.login(cred)).thenReturn(pri);
		
		assertEquals(pri, new UserController(userService).login(cred));
	}
	
	@Test
	public void unitTestLoginNullCred() throws Throwable {
		Credentials cred = null;
		Principal pri = createPrincipal();
		
		when(userService.login(cred)).thenReturn(pri);
		
		assertThrows(Exception.class, () -> {
			new UserController(userService).login(cred);
		});
	}
	
	@Test
	public void integrationTestLogin() throws Throwable {
		Credentials cred = new Credentials("usertest", "pass");
		Principal pri = createPrincipal();
		
		when(userService.login(cred)).thenReturn(pri);
		
		String requestJson = TestUtils.objToJson(cred);
		logger.info("Cred: " + requestJson);
		String responseJson = TestUtils.objToJson(pri);
		logger.info("Principal: " + responseJson);
		
		mvc.perform(
					post("/users/login")
					.contentType(new MediaType(
							MediaType.APPLICATION_JSON.getType(),
							MediaType.APPLICATION_JSON.getSubtype(),
							Charset.forName("utf8"))
					)
					.content(requestJson)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(responseJson)));
	}
	
	@Test
	public void unitTestAuth() {
		Principal pri = createPrincipal();
		when(mockreq.getAttribute("principal")).thenReturn(pri);
		when(userService.auth(pri)).thenReturn(pri);
		
		assertEquals(pri, new UserController(userService).auth(mockreq, null));
	}
	
	@Test
	public void unitTestAuthBadPrincipal() {
		Principal pri = createPrincipal();
		pri.setUsername(null);
		when(mockreq.getAttribute("principal")).thenReturn(pri);
		when(userService.auth(pri)).thenReturn(pri);
		
		assertThrows(BadRequest.class, () -> {
			new UserController(userService).auth(mockreq, null);
		});
	}
	
	@Test
	public void unitTestAuthNullPrincipal() {
		Principal pri = null;
		when(mockreq.getAttribute("principal")).thenReturn(pri);
		when(userService.auth(pri)).thenReturn(pri);
		
		assertThrows(BadRequest.class, () -> {
			new UserController(userService).auth(mockreq, null);
		});
	}
	
}
