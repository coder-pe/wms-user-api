package com.hellozum.challenge.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellozum.challenge.api.dto.UserDto;
import com.hellozum.challenge.model.User;
import com.hellozum.challenge.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "User rest controller that supports CRUD operations")
public class UserResource {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Create new user", response = User.class)
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) throws Exception {
		UserDto savedUser = userService.createUser(user);
		return ResponseEntity.ok(savedUser);
	}
	
	@ApiOperation(value = "Update existing user", response = User.class)
	@PutMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) throws Exception {
		UserDto updatedUser = userService.updateUser(user);
		
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "Get user by Id", response = User.class)
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer id) throws Exception {
		UserDto user = userService.getUserById(id);
		
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "Get paged users", response = Page.class)
	@GetMapping(value = "/users/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<UserDto>> getPagedUsers(@PathVariable("page") Integer pageNumber,
			@PathVariable("size") Integer pageSize) throws Exception {
		Page<UserDto> pagedUsers = userService.getPagenatedUsers(pageNumber, pageSize);
		return ResponseEntity.ok(pagedUsers);
	}
	
	@ApiOperation(value = "Get all users", response = List.class)
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDto>> getAllUsers() throws Exception {
		List<UserDto> allUsers = userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	@ApiOperation(value = "Delete user by Id", response = Void.class)
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) throws Exception {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
}
