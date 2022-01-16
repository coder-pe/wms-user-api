package com.hellozum.challenge.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hellozum.challenge.api.dto.UserDto;
import com.hellozum.challenge.api.dto.mapper.UserDtoMapper;
import com.hellozum.challenge.model.Address;
import com.hellozum.challenge.model.User;
import com.hellozum.challenge.repository.UserRepository;
import com.hellozum.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${application.max-page-size}")
	private Integer maxPageSize;
	
	private UserDtoMapper userMapper = new UserDtoMapper();

	@Transactional
	@Override
	public UserDto createUser(UserDto userDto) throws Exception {
		User user = userMapper.toEntity(userDto);
		User savedUser = userRepository.save(user);
		return userMapper.toDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto) throws Exception {
		if (userDto == null || userDto.getId() == null) {
			return null;
		}
		
		Optional<User> optSavedUser = userRepository.findById(userDto.getId());
		
		if (optSavedUser.isEmpty()) {
			return null;
		}
		
		User savedUser = optSavedUser.get();
		
		if (userDto.getName() != null && !userDto.getName().equals(savedUser.getName())) {
			savedUser.setName(userDto.getName());
		}
		
		if (userDto.getEmail() != null && !userDto.getEmail().equals(savedUser.getEmail())) {
			savedUser.setEmail(userDto.getEmail());
		}
		
		if (userDto.getBirthDate() != null && !userDto.getBirthDate().equals(savedUser.getBirthDate())) {
			savedUser.setBirthDate(userDto.getBirthDate());
		}
		
		if (userDto.getAddress() != null) {
			Address savedAddress = savedUser.getAddress();
			
			if (savedAddress == null) {
				savedAddress = new Address();
			}

			if (userDto.getAddress().getStreet() != null 
					&& !userDto.getAddress().getStreet().equals(savedAddress.getStreet())) {
				savedAddress.setStreet(userDto.getAddress().getStreet());
			}
			
			if (userDto.getAddress().getNumber() != null 
					&& !userDto.getAddress().getNumber().equals(savedAddress.getNumber())) {
				savedAddress.setNumber(userDto.getAddress().getNumber());
			}
			
			savedUser.setAddress(savedAddress);
		}
		
		User updatedUser = userRepository.save(savedUser);
		UserDto updatedUserDto = userMapper.toDto(updatedUser);
		
		return updatedUserDto;
	}
	
	@Override
	public UserDto getUserById(Integer id) throws Exception {
		User user = userRepository.findById(id).orElse(null);
		return userMapper.toDto(user);
	}
	
	@Override
	public void deleteUser(Integer id) throws Exception {
		userRepository.deleteById(id);
	}
	
	@Override
	public Page<UserDto> getPagenatedUsers(Integer pageNumber, Integer pageSize) throws Exception {
		if (pageNumber == null || pageNumber < 0) {
			pageNumber = 0;
		}
		
		if (pageSize == null || pageSize < 0 || pageSize > maxPageSize) {
			pageSize = maxPageSize;
		}
		
		Pageable page = PageRequest.of(pageNumber, pageSize);
		
		Page<User> pagedUsers = userRepository.findAll(page);
		
		return userMapper.toDto(pagedUsers);
	}
	
	@Override
	public List<UserDto> getAllUsers() throws Exception {
		List<User> users = userRepository.findAll();
		return userMapper.toDto(users);
	}
}
