package com.hellozum.challenge.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hellozum.challenge.api.dto.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto) throws Exception;

	UserDto updateUser(UserDto userDto) throws Exception;

	UserDto getUserById(Integer id) throws Exception;

	void deleteUser(Integer id) throws Exception;

	Page<UserDto> getPagenatedUsers(Integer pageNumber, Integer pageSize) throws Exception;

	List<UserDto> getAllUsers() throws Exception;
}
