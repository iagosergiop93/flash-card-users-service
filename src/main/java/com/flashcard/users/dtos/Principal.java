package com.flashcard.users.dtos;

import com.flashcard.users.entities.Role;
import com.flashcard.users.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Principal {
	
	private long id;
	private String firstName;
	private String lastName;
	private String username;
	private Role role;
	
	public Principal(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.role = user.getRole();
	}

	@Override
	public String toString() {
		return "Principal [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", role=" + role + "]";
	}
	
}
