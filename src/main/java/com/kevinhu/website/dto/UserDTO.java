package com.kevinhu.website.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class UserDTO {

	private Long id;
	@NotNull(message = "Must enter a username")
	private String username;
	private String email;
	private List<RoleDTO> roles;
	private List<String> rolesName;
	private List<AddressDTO> addresses;
	private List<PhoneDTO> phones;
	private String newPassword;
	private String confirmPassword;
	private String currentPassword;
	private List<String> selectedRoleNames = new ArrayList<String>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}
	public List<String> getRolesName() {
		return rolesName;
	}
	public void setRolesName(List<String> rolesName) {
		this.rolesName = rolesName;
	}
	public List<AddressDTO> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}
	public List<PhoneDTO> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneDTO> phone) {
		this.phones = phone;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public List<String> getSelectedRoleNames() {
		return selectedRoleNames;
	}
	public void setSelectedRoleNames(List<String> selectedRoleNames) {
		this.selectedRoleNames = selectedRoleNames;
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", email=" + email + ", roles=" + roles + ", rolesName="
				+ rolesName + ", addresses=" + addresses + ", phones=" + phones + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + ", currentPassword=" + currentPassword
				+ ", selectedRoleNames=" + selectedRoleNames + "]";
	}

	
	
}
