package com.flashcard.users.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class BaseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private Error error;
	
	public BaseException(int code, String message) {
		this.error = new Error(code, message);
	}
	
}

@Getter @Setter
class Error {
	private int code;
	private String message;
	
	public Error(int code, String msg) {
		this.code = code;
		this.message = msg;
	}
	
}