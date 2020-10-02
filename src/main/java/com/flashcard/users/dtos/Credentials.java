package com.flashcard.users.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Credentials {
	
	private String username;
	private String passwd;
	
	public Credentials(String username, String passwd) {
		super();
		this.username = username;
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", passwd=" + passwd + "]";
	}
	
}
