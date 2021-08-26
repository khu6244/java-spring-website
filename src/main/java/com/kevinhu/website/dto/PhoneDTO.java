package com.kevinhu.website.dto;

public class PhoneDTO {

	private Long id;
	private String countryCode;
	private String areaCode;
	private String prefix;
	private String lineNumber;
	private String extension;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	@Override
	public String toString() {
		return "PhoneDTO [id=" + id + ", countryCode=" + countryCode + ", areaCode=" + areaCode + ", prefix=" + prefix
				+ ", lineNumber=" + lineNumber + ", extension=" + extension + "]";
	}
	
	
	
}
