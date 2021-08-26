package com.kevinhu.website;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override // override annotation means the class does this instead of what is written in the parent class
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebsiteApplication.class);
		// pretty sure that this goes through, reads annotations, and prepares/builds the application
	}

}
