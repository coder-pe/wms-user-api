package com.hellozum.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.hellozum.challenge.api.dto.AddressDto;
import com.hellozum.challenge.api.dto.UserDto;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	private UserDto savedUser;
	private UserDto updatedUser;

	@Test
	@Order(1)
	public void createUser() throws Exception {
		UserDto user = new UserDto();
		user.setName("Edgar Miguel Mamani Condori");
		user.setEmail("miguel.coder.pe@gmail.com");
		user.setBirthDate("29/12/1981");
		
		AddressDto address = new AddressDto();
		address.setStreet("Jr Los Granates");
		address.setNumber(2);
		
		user.setAddress(address);
		
		savedUser = userService.createUser(user);
		
		assertNotNull(savedUser);
		assertNotNull(savedUser.getId());
		assertEquals(user.getEmail(), savedUser.getEmail());
	}
	
	@Test
	@Order(2)
	public void updateUser() throws Exception {
		UserDto toUpdateUser = new UserDto();
		toUpdateUser.setId(savedUser.getId());
		toUpdateUser.setName("Miguel Mamani");
		toUpdateUser.setEmail("edgar.mamani.pe@gmail.com");
		toUpdateUser.setBirthDate("28/12/1981");
		
		AddressDto toUpdateAddress = new AddressDto();
		toUpdateAddress.setStreet("Jr Los Girasoles");
		toUpdateAddress.setNumber(23);
		toUpdateUser.setAddress(toUpdateAddress);
		
		updatedUser = userService.updateUser(toUpdateUser);
		
		assertNotNull(updatedUser);
		assertNotNull(updatedUser.getId());
		assertEquals(toUpdateUser.getName(), updatedUser.getName());
		assertEquals(toUpdateUser.getEmail(), updatedUser.getEmail());
		assertEquals(toUpdateUser.getBirthDate(), updatedUser.getBirthDate());
		assertEquals(toUpdateUser.getAddress().getNumber(), updatedUser.getAddress().getNumber());
		assertEquals(toUpdateUser.getAddress().getStreet(), updatedUser.getAddress().getStreet());
	}
	
	@Test
	@Order(3)
	public void updateUserEmailStreet() throws Exception {
		UserDto toUpdateUser = new UserDto();
		toUpdateUser.setId(savedUser.getId());
		toUpdateUser.setEmail("edgar.mamani@outlook.com");
		
		AddressDto toUpdateAddress = new AddressDto();
		toUpdateAddress.setStreet("Jr Los Tulipanes");
		toUpdateUser.setAddress(toUpdateAddress);
		
		UserDto updatedUserV2 = userService.updateUser(toUpdateUser);
		
		assertNotNull(updatedUserV2);
		assertNotNull(updatedUserV2.getId());
		assertEquals(updatedUser.getName(), updatedUserV2.getName());
		assertEquals(toUpdateUser.getEmail(), updatedUserV2.getEmail());
		assertEquals(updatedUser.getBirthDate(), updatedUserV2.getBirthDate());
		assertEquals(toUpdateUser.getAddress().getStreet(), updatedUserV2.getAddress().getStreet());
	}
	
	@Test
	@Order(4)
	public void getUserById() throws Exception {
		UserDto user = userService.getUserById(savedUser.getId());
		
		assertNotNull(user);
	}
	
	@Test
	@Order(5)
	public void getUserNotExists() throws Exception {
		UserDto user = userService.getUserById(82432);
		
		assertNull(user);
	}
	
	@Test
	@Order(6)
	public void getPagenatedUsers() throws Exception {
		Integer pageNumber = 0;
		Integer pageSize = 10;
		Page<UserDto> users = userService.getPagenatedUsers(pageNumber, pageSize);
		
		assertNotNull(users);
		assertEquals(true, users.hasContent());
	}
	
	@Test
	@Order(7)
	public void deleteUserById() throws Exception {
		userService.deleteUser(savedUser.getId());
		
		UserDto user = userService.getUserById(savedUser.getId());
		assertNull(user);
	}
	
	
}
