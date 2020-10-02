package com.flashcard.users.utils.validators;

import org.springframework.stereotype.Component;

import com.flashcard.users.dtos.Credentials;
import com.flashcard.users.exceptions.BadRequest;

@Component
public class CredentialsValidator {
	
	public static void validate(Credentials obj) throws BadRequest {
		if(obj.getUsername() == null || obj.getUsername().equals("") ||
				obj.getPasswd() == null || obj.getPasswd().equals("")) {
			
			throw new BadRequest(400, "Missing field");
		}
		
	}
	
}
