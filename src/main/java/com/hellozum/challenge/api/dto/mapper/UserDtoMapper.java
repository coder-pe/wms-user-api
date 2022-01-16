package com.hellozum.challenge.api.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.hellozum.challenge.api.dto.UserDto;
import com.hellozum.challenge.model.User;

public final class UserDtoMapper {
	
	private AddressDtoMapper addressMapper = new AddressDtoMapper();

	// Is not static for thread-safe
	public UserDto toDto(User user) {
		if (user == null) {
			return null;
		}
		
		UserDto userDto = new UserDto();
		userDto.setAddress(addressMapper.toDto(user.getAddress()));
		userDto.setBirthDate(user.getBirthDate());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		
		return userDto;
	}
	
	public User toEntity(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		
		User user = new User();
		user.setAddress(addressMapper.toEntity(userDto.getAddress()));
		user.setBirthDate(userDto.getBirthDate());
		user.setEmail(userDto.getEmail());
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		
		return user;
	}

	public Page<UserDto> toDto(Page<User> pagedUsers) {
		if (pagedUsers == null) {
			return null;
		}
		
		List<User> users = pagedUsers.getContent();
		
		List<UserDto> usersDto 
			= users.stream()
				.map(user -> toDto(user))
				.collect(Collectors.toList());
		
		Page<UserDto> pagedUsersDto 
			= new PageImpl<UserDto>(usersDto, pagedUsers.getPageable(), pagedUsers.getTotalElements());
		
		return pagedUsersDto;
	}

	public List<UserDto> toDto(List<User> users) {
		if (users == null) {
			return null;
		}
		
		List<UserDto> usersDto 
		= users.stream()
			.map(user -> toDto(user))
			.collect(Collectors.toList());
		
		return usersDto;
	}
}
