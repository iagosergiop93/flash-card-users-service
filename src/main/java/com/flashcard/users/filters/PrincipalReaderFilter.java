package com.flashcard.users.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashcard.users.dtos.Principal;
import com.flashcard.users.utils.validators.PrincipalValidator;

@Component
@Order(1)
public class PrincipalReaderFilter extends HttpFilter {
	
	private static final long serialVersionUID = -382021497348635065L;
	
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
			throws IOException, ServletException {
		
		String token = req.getHeader("authorization");
		if(token != null && !token.equals("")) {
			Principal principal = readPrincipal(req);
			PrincipalValidator.validate(principal);
			req.setAttribute("principal", principal);
		}
		
		chain.doFilter(req, res);
	}
	
	public Principal readPrincipal(HttpServletRequest req) {
		String principalStr= req.getHeader("principal");
		ObjectMapper mapper = new ObjectMapper();
		try {
			Principal principal = mapper.readValue(principalStr, Principal.class);
			return principal;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
