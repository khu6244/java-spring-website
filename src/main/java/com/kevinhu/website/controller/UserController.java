package com.kevinhu.website.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevinhu.website.dto.IdPair;
import com.kevinhu.website.dto.RoleDTO;
import com.kevinhu.website.dto.UserDTO;
import com.kevinhu.website.service.RoleService;
import com.kevinhu.website.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private RoleService roleService;
//	private AddressService addressService;
//	private PhoneService phoneService;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	private final static int PAGE_SIZE = 5;
	
	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		this.roleService = roleService;
		this.userService = userService;
//		this.addressService = addressService;
//		this.phoneService = phoneService;
	}
	
	@GetMapping("")
	public String index (Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		
		int currentPage = 1;
		if (page.isPresent()) {
			currentPage=page.get();
		}
		
		Pageable pageable = PageRequest.of(currentPage - 1, PAGE_SIZE);
		Page<UserDTO> userPage = userService.getAllUsers(pageable);
		
		model.addAttribute("userPage", userPage);

		List<String> allRoles = roleService.getAllRoles().stream()
				.map(x->x.getName()).collect(Collectors.toList());
				
		model.addAttribute("allRoles", allRoles);
		
		int totalPages = userPage.getTotalPages();
		if (totalPages>0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		
		return "users";
	}
	
	@GetMapping(value="/userdetails/{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		UserDTO user = userService.getUserById(id);
		
		user.setRolesName(user.getRoles().stream().map(x->x.getName()).collect(Collectors.toList()));
		user.getSelectedRoleNames().addAll(user.getRolesName());
		
		
		List<RoleDTO> roles = roleService.getAllRoles();
		
		
		model.addAttribute("user", user);
		model.addAttribute("allRoleNames",roles.stream().map(x->x.getName()).collect(Collectors.toList()));
		
		UserDetails userDetail = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("isLoggedinUser", userDetail.getUsername().equals(user.getEmail()));
		
		return "userdetails";
	}
	
	
	
	@PostMapping(value = "/save")
	public String save(UserDTO userDTO, Model model) {
		logger.info(userDTO.toString());
		try {
			userService.editUser(userDTO);
		}catch(Exception ex) {
			model.addAttribute("errorMsg", ex.getMessage());		
		}
		return "redirect:/users";
		
	}
	
	@GetMapping(value="/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		userService.deleteUser(id);
		
		return "redirect:/users";
	
	}
	
	
	//this is mandatory for ajax call: produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE
	@PostMapping(value="/deletePhone",produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody //this is mandatory for ajax call
	public Object deletePhone(@RequestBody IdPair ids) {
		userService.deleteUserPhone(ids);
		JSONObject jo = new JSONObject();
		jo.put("responseText", "removed " + ids.getUid() + ";" + ids.getId());
		
		return jo.toString();
		
	
	}
	
	@PostMapping(value = "/deleteAddress", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object deleteAddress(@RequestBody IdPair ids) {
		userService.deleteUserAddress(ids);
		JSONObject jo = new JSONObject();
		jo.put("responseText", "removed" + ids.getUid() + ";" + ids.getId());
		
		return jo.toString();
	}
	
}
