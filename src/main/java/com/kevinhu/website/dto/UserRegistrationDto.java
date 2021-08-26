package com.kevinhu.website.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.kevinhu.website.validation.FieldMatch;

@FieldMatch.List({
	@FieldMatch(first = "password", second = "confirmPassword", message = "Password confirmation must match password")
})

@Component
public class UserRegistrationDto {
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String confirmPassword;
	
	@Email
	@NotEmpty
	private String email;
	
	@AssertTrue
	private Boolean terms;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getTerms() {
		return terms;
	}

	public void setTerms(Boolean terms) {
		this.terms = terms;
	}
	
}
