package com.kevinhu.website.dto;

import javax.validation.constraints.NotNull;

public class AddressDTO {

	private Long id;
	private Long houseNumber;
	private Long apartment;
	private String street;
	private String city;
	private String province;
	private String country;
	private String postal;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(Long houseNumber) {
		this.houseNumber = houseNumber;
	}
	public Long getApartment() {
		return apartment;
	}
	public void setApartment(Long apartment) {
		this.apartment = apartment;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", houseNumber=" + houseNumber + ", apartment=" + apartment + ", street="
				+ street + ", city=" + city + ", province=" + province + ", country=" + country + ", postal=" + postal
				+ "]";
	}
	


	
	
	
}
