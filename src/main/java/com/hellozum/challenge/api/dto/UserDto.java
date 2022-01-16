package com.hellozum.challenge.api.dto;

import javax.validation.constraints.NotBlank;

public class UserDto {
	private Integer id;
	
	@NotBlank(message = "User name is mandatory")
	private String name;
	
	@NotBlank(message = "User email is mandatory")
	private String email;
	
	@NotBlank(message = "User birth date is mandatory")
	private String birthDate;
	
	private AddressDto address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}
}
