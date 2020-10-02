package com.flashcard.users.exceptions;

public class BadRequest extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public BadRequest(int code, String msg) {
		super(code, msg);
	}
	
}
