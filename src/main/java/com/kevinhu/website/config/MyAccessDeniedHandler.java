package com.kevinhu.website.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component // custom bean
public class MyAccessDeniedHandler implements AccessDeniedHandler{

	private static Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);
	// initialize the logger, this is used for both console output as well as generating a log file
	
	@Override
	public void handle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// when a request is generated, this goes and checks
		
		if (authentication != null) {
			// I suppose that only authentication errors return a non-null value
			// so log any unauthorized access attempts
			logger.info("'" + authentication.getName() + "'" + "was trying to access protected url "
							+ httpServletRequest.getRequestURI());
		}
		
		// and then redirect to error page
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/access-denied");
		
	}
	
}
