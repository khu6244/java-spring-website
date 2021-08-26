package com.kevinhu.website.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kevinhu.website.dto.UserRegistrationDto;
import com.kevinhu.website.model.User;
import com.kevinhu.website.service.UserService;

@Controller
public class LoginController {

	private UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/access-denied")
	public String accessDenied() {
		return "error/access-denied";
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping(value = "/")
	public String showDefault(Model model) {
		return showRegistrationForm(model);
	}

	@GetMapping(value = "/login")
	public String showRegistrationForm(Model model) {
		return "login";
	}

	// need this until project controller has the correct mapping
	// //home doesn't go straight to the html, it submits a request to the
	// controller which then needs to return home
	@GetMapping(value = "/home")
	public String showHome(Model model) {

		// "jpegtest.jpg" should get from database, not hard code here
		model.addAttribute("imageName", "jpegtest.jpg");

		return "home";
	}

	@PostMapping(value = "/registration")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result,
			Model model) {

		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "login";
		}

		userService.save(userDto);

		return "redirect:/login?success";

	}

}
