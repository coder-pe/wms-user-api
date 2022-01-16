package com.hellozum.challenge.api.dto;

import javax.validation.constraints.NotBlank;

public class AddressDto {
	private Integer id;
	
	@NotBlank(message = "Street is mandatory")
	private String street;
	
	@NotBlank(message = "Number is mandatory")
	private Integer number;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
}
