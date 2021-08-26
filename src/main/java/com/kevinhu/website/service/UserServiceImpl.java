package com.kevinhu.website.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevinhu.website.dto.AddressDTO;
import com.kevinhu.website.dto.IdPair;
import com.kevinhu.website.dto.PhoneDTO;
import com.kevinhu.website.dto.UserDTO;
import com.kevinhu.website.dto.UserRegistrationDto;
import com.kevinhu.website.model.Address;
import com.kevinhu.website.model.Phone;
import com.kevinhu.website.model.Role;
import com.kevinhu.website.model.User;
import com.kevinhu.website.repository.AddressRepository;
import com.kevinhu.website.repository.PhoneRepository;
import com.kevinhu.website.repository.RoleRepository;
import com.kevinhu.website.repository.UserRepository;
import com.kevinhu.website.util.StringUtil;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private PhoneRepository phoneRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder, AddressRepository addressRepository,
			PhoneRepository phoneRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.phoneRepository = phoneRepository;
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(UserRegistrationDto registration) {

		User user = new User();
		user.setUsername(registration.getUsername());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		Role role = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(role));

		return userRepository.save(user);

	}

	//the method loadUserByUsername in the UserDetailsService is used by SecurityConfig class for authenticationProvider
	//the method loadUserByUsername is implemented in the UserServiceImpl class which to get the user information such as roles.
	@Override
	public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
		logger.info("loadUserByUsername:" + emailOrUsername);

		User user = userRepository.findByUsername(emailOrUsername);
		if (user == null) {
			user = userRepository.findByEmail(emailOrUsername);
			if (user == null) {
				logger.error("user cannot be found by:" + emailOrUsername);
				throw new UsernameNotFoundException("Invalid username or email.");
			}

		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
	}

	@Override
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		Page<UserDTO> userPage = userRepository.findAll(pageable).map(x -> modelMapper.map(x, UserDTO.class));

		for (UserDTO u : userPage.getContent()) {
			u.setRolesName(u.getRoles().stream().map(y -> y.getName()).collect(Collectors.toList()));
		}

		return userPage;
	}

	@Override
	public UserDTO getUserById(Long id) {

		Optional<User> user = userRepository.findById(id);
//		if(!user.isPresent()) {
//			//log
//			//throw new exception:
//		}

		UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);

		if (userDTO.getAddresses() == null || userDTO.getAddresses().isEmpty()) {
			AddressDTO aDTO1 = new AddressDTO();
			AddressDTO aDTO2 = new AddressDTO();

			List<AddressDTO> addressList = new ArrayList<AddressDTO>();
			addressList.add(aDTO1);
			addressList.add(aDTO2);

			userDTO.setAddresses(addressList);
		} else if (userDTO.getAddresses().size() == 1) {
			AddressDTO aDTO1 = new AddressDTO();

			List<AddressDTO> addressList = userDTO.getAddresses();
			addressList.add(aDTO1);

		}

		if (userDTO.getPhones() == null || userDTO.getPhones().isEmpty()) {
			PhoneDTO pDTO1 = new PhoneDTO();
			PhoneDTO pDTO2 = new PhoneDTO();

			List<PhoneDTO> phoneList = new ArrayList<PhoneDTO>();
			phoneList.add(pDTO1);
			phoneList.add(pDTO2);

			userDTO.setPhones(phoneList);
		} else if (userDTO.getPhones().size() == 1) {
			PhoneDTO aDTO1 = new PhoneDTO();

			List<PhoneDTO> phoneList = userDTO.getPhones();
			phoneList.add(aDTO1);

		}

		return userDTO;
	}

	@Transactional
	@Override
	public void editUser(UserDTO userDTO) throws Exception {
		if (!isLoggedUser(userDTO) && !isAdmin()) {
			throw new Exception("You cannot change another user's information");
		}

		User user = userRepository.getById(userDTO.getId());
		User u = new User();

		if (isLoggedUser(userDTO)) {
			checkSetPassword(userDTO, user, u);
		} else {
			u.setPassword(user.getPassword());
		}

		checkSetAddress(userDTO, u);

		checkSetPhone(userDTO, u);

		if (isAdmin()) {
			checkSetRole(userDTO, u);
		}

		u.setId(userDTO.getId());
		u.setEmail(userDTO.getEmail());
		u.setUsername(userDTO.getUsername());

		userRepository.save(u);
	}

	private void checkSetRole(UserDTO userDTO, User u) {
		boolean hasAdmin = isAdmin();
		if (hasAdmin) {
			if (u.getRoles() == null) {
				u.setRoles(new ArrayList());
			}
			for (String roleName : userDTO.getSelectedRoleNames()) {
				Role r = roleRepository.findByName(roleName);
				u.getRoles().add(r);
			}

		}
	}

	private boolean isAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean hasAdmin = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));
		return hasAdmin;
	}

	private boolean isLoggedUser(UserDTO userDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUsername = ((org.springframework.security.core.userdetails.User) auth.getPrincipal())
				.getUsername();
		String editedUser = userDTO.getEmail();
		return editedUser.equals(loggedInUsername);
	}

	private void checkSetPassword(UserDTO userDTO, User user, User u) throws Exception {

		Boolean newPassword;

		Boolean match = passwordEncoder.matches(userDTO.getCurrentPassword(), user.getPassword());

		if (!match) { // checks if the new password and new password confirmation match
			throw new Exception("Does not match current password");
		}

		if (userDTO.getNewPassword() == "" && userDTO.getConfirmPassword() == "") { // checks if there has been a
																					// password change submitted at all
			newPassword = false;
		} else {
			newPassword = true;
		}

		if (newPassword == true && !(userDTO.getNewPassword().equals(userDTO.getConfirmPassword()))) { //
			throw new Exception("Password confirmation does not match");
		}

		if (newPassword == true) {
			u.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
		} else {
			u.setPassword(user.getPassword());
		}
	}

	private void checkSetPhone(UserDTO userDTO, User u) {
		if (userDTO.getPhones() == null) {
			return;
		}

		for (PhoneDTO phoneDTO : userDTO.getPhones()) {
			if (!isPhoneDTOEmpty(phoneDTO)) {
				Phone phone = modelMapper.map(phoneDTO, Phone.class);				
				
				List<Phone> result = phoneRepository.findPhone(phone);
				if (result != null && !result.isEmpty()) {
					phone.setId(result.get(0).getId());
				}
				
				if(u.getPhones() == null)
					u.setPhones(new ArrayList<Phone>());
				u.getPhones().add(phone);
			}
		}		
	}

	private void checkSetAddress(UserDTO userDTO, User u) {
		if (userDTO.getAddresses() == null) {
			return;
		}

		for (AddressDTO addressDTO : userDTO.getAddresses()) {
			if (!isAddressDTOEmpty(addressDTO)) {
				Address address = modelMapper.map(addressDTO, Address.class);				
				
				List<Address> result = addressRepository.findAddress(address);
				if (result != null && !result.isEmpty()) {
					address.setId(result.get(0).getId());
				}
				
				if(u.getAddresses() == null)
					u.setAddresses(new ArrayList<Address>());
				
				u.getAddresses().add(address);
			}
		}		
	}

	private Boolean isAddressDTOEmpty(AddressDTO address) {
		if (StringUtil.isBlank(address.getCity()) && StringUtil.isBlank(address.getCountry())
				&& StringUtil.isBlank(address.getPostal()) && StringUtil.isBlank(address.getProvince())
				&& StringUtil.isBlank(address.getStreet()) && address.getHouseNumber() == null
				&& address.getApartment() == null)
			return true;
		return false;
	}

	private Boolean isPhoneDTOEmpty(PhoneDTO phone) {
		if (StringUtil.isBlank(phone.getAreaCode()) && StringUtil.isBlank(phone.getCountryCode())
				&& StringUtil.isBlank(phone.getExtension()) && StringUtil.isBlank(phone.getLineNumber())
				&& StringUtil.isBlank(phone.getPrefix())) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}
	
	public void deleteUserPhone(IdPair ids) {
		User user = userRepository.getById(ids.getUid());
		Phone phone = phoneRepository.getById(ids.getId());
		user.getPhones().remove(phone);
		
		userRepository.save(user);
	}

	public void deleteUserAddress(IdPair ids) {
		User user = userRepository.getById(ids.getUid());
		Address address = addressRepository.getById(ids.getId());
		user.getAddresses().remove(address);
		
		userRepository.save(user);
		
	}

}
