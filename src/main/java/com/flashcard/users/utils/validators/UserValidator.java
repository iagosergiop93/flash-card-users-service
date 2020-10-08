package com.flashcard.users.utils.validators;

import com.flashcard.users.entities.User;
import com.flashcard.users.exceptions.BadRequest;

public class UserValidator {
	
	public static void validate(User obj) throws BadRequest {
		if(obj == null || 
				obj.getUsername() == null || obj.getUsername().equals("") || 
				obj.getFirstName() == null || obj.getFirstName().equals("") ||
				obj.getLastName() == null || obj.getLastName().equals("")) {
			
			throw new BadRequest(400, "Missing field");
		}
		
	}
	
}
