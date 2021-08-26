package com.kevinhu.website.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.kevinhu.website.model.User;
import com.kevinhu.website.dto.IdPair;
import com.kevinhu.website.dto.UserDTO;
import com.kevinhu.website.dto.UserRegistrationDto;

//the method loadUserByUsername in the UserDetailsService is used by SecurityConfig class for authenticationProvider
//the method loadUserByUsername is implemented in the UserServiceImpl class which to get the user information such as roles.
public interface UserService extends UserDetailsService{
	
	User findByEmail(String email);
	User save(UserRegistrationDto userRegistrationDto);
	Page<UserDTO> getAllUsers(Pageable pageable);
	UserDTO getUserById(Long id);
	void editUser(UserDTO userDTO) throws Exception;
	void deleteUser(Long id);
	void deleteUserPhone(IdPair ids);
	void deleteUserAddress(IdPair ids);
	
}