package com.flashcard.users.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashcard.users.dtos.Credentials;
import com.flashcard.users.dtos.Principal;
import com.flashcard.users.entities.Role;
import com.flashcard.users.entities.User;
import com.flashcard.users.exceptions.BadRequest;
import com.flashcard.users.exceptions.ServerError;
import com.flashcard.users.repositories.UserRepository;
import com.flashcard.users.utils.EncryptUtil;
import com.flashcard.users.utils.validators.CredentialsValidator;

@Service
public class UserService {
	
	UserRepository userRepo;
	CredentialsValidator credentialsValidator;
	
	@Autowired
	public UserService(UserRepository userRepo, CredentialsValidator credentialsValidator) {
		super();
		this.userRepo = userRepo;
		this.credentialsValidator = credentialsValidator;
	}

	public List<User> getAll() throws RuntimeException {
		
		return userRepo.findAll();
		
	}
	
	public Principal auth(Principal principal) throws RuntimeException {
		
		User user = null;
		
		try {
			
			String username = principal.getUsername();
			user = userRepo.findByUsername(username);
			
		} catch(RuntimeException e) {
			throw e;
		}
		
		return new Principal(user);
	}
	
	public Principal login(Credentials cred) throws Throwable {
		User user = null;
		
		try {
			CredentialsValidator.validate(cred);
			
			user = userRepo.findByUsername(cred.getUsername());
			
			if(user == null || !EncryptUtil.checkHash(cred.getPasswd(), user.getPasswd())) {
				throw new BadRequest(400, "The username or the password is wrong");
			}
			
		} catch(BadRequest e) {
			e.printStackTrace();
			throw e;
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw new ServerError(500, e.getMessage());
		}
		
		return new Principal(user);
	}
	
	@Transactional
	public Principal createUser(User user) throws RuntimeException {
		
		try {
			
			// Set Role
			user.setRole(new Role(1, "USER"));
			
			// Hash Passwd
			String hash = EncryptUtil.createHash(user.getPasswd());
			user.setPasswd(hash);
			
			// Persist User
			user = userRepo.save(user);
			userRepo.flush();
			
		} catch(RuntimeException e) {
			throw e;
		}
		
		return new Principal(user);
	}
	
}
