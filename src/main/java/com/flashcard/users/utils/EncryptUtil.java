package com.flashcard.users.utils;

import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncryptUtil {
	
	public static String createHash(String word) {
		
		byte[] byteArr = DatatypeConverter.parseBase64Binary(word);
		String hash = BCrypt.hashpw(byteArr, BCrypt.gensalt(16));
		
		return hash;
	}
	
	public static boolean checkHash(String word, String hash) {
		
		byte[] byteArr = DatatypeConverter.parseBase64Binary(word);
		return BCrypt.checkpw(byteArr, hash);
	}
	
}
