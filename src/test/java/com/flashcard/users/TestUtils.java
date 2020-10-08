package com.flashcard.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestUtils {
	
	public static String objToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer();
	    String requestJson = ow.writeValueAsString(obj);
	    
	    return requestJson;
	}
	
}
