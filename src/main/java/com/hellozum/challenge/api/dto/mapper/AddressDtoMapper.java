package com.hellozum.challenge.api.dto.mapper;

import com.hellozum.challenge.api.dto.AddressDto;
import com.hellozum.challenge.model.Address;

public final class AddressDtoMapper {

	public AddressDto toDto(Address address) {
		if (address == null) {
			return null;
		}
		
		AddressDto addressDto = new AddressDto();
		addressDto.setId(address.getId());
		addressDto.setNumber(address.getNumber());
		addressDto.setStreet(address.getStreet());
		return addressDto;
	}
	
	public Address toEntity(AddressDto addressDto) {
		if (addressDto == null) {
			return null;
		}
		
		Address address = new Address();
		address.setId(addressDto.getId());
		address.setNumber(addressDto.getNumber());
		address.setStreet(addressDto.getStreet());
		return address;
	}
}
