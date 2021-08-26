package com.kevinhu.website.model;

import javax.persistence.*;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Long houseNumber; 
	@Column
	private Long apartment;
	@Column
	private String street;
	@Column
	private String city;
	@Column
	private String province;
	@Column
	private String country;
	@Column(nullable = false)
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
		return "Address [id=" + id + ", houseNumber=" + houseNumber + ", apartment=" + apartment + ", street=" + street
				+ ", city=" + city + ", province=" + province + ", country=" + country + ", postal=" + postal + "]";
	}
	public Long getApartment() {
		return apartment;
	}
	public void setApartment(Long apartment) {
		this.apartment = apartment;
	}
	


	
	
}
