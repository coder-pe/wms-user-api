package com.hellozum.challenge.api;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.hellozum.challenge.AbstractTests;
import com.hellozum.challenge.model.Address;
import com.hellozum.challenge.model.User;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTests extends AbstractTests {
	@Autowired
	private MockMvc mockMvc;
	
	private User user;
	
	@BeforeAll
	public void setUp() {
		user = new User();
		user.setName("Patricia Salas");
		user.setEmail("patricia@gmail.com");
		user.setBirthDate("20/12/1984");
		
		Address address = new Address();
		address.setStreet("Av Colonial");
		address.setNumber(1230);
		user.setAddress(address);
	}
	
	@Test
	@Order(1)
	@DisplayName("POST /api/user")
	public void createUser() throws Exception {
		// Mock request
		MvcResult result = this.mockMvc
			.perform(
					post("/api/user")
					.contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(user)))
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			// Validate returned properties
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.name", is(user.getName())))
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.birthDate", is(user.getBirthDate())))
            .andExpect(jsonPath("$.address.id", is(notNullValue())))
            .andExpect(jsonPath("$.address.street", is(user.getAddress().getStreet())))
            .andExpect(jsonPath("$.address.number", is(user.getAddress().getNumber())))
            .andReturn();
		
		// For next test
		Integer userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		Integer addressId = JsonPath.read(result.getResponse().getContentAsString(), "$.address.id");
		user.setId(userId);
		user.getAddress().setId(addressId);
	}
	
	@Test
	@Order(2)
	@DisplayName("PUT /api/user")
	public void updateUser() throws Exception {
		User userToUpd = new User();
		userToUpd.setId(user.getId());
		userToUpd.setName("Miguel Mamani");
		
		// Mock request
		MvcResult result = this.mockMvc
			.perform(
					put("/api/user")
					.contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(userToUpd)))
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			// Validate returned properties
            .andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.address.id", is(notNullValue())))
            .andExpect(jsonPath("$.name", is(userToUpd.getName())))
            .andReturn();
		
			// For next test
			String name = JsonPath.read(result.getResponse().getContentAsString(), "$.name");
			user.setName(name);
	}
	
	@Test
	@Order(3)
	@DisplayName("PUT /api/user")
	public void tryUpdateUserNotFound() throws Exception {
		User userToUpd = new User();
		userToUpd.setId(100000);
		userToUpd.setName("Miguel Mamani");
		
		// Mock request
		this.mockMvc
			.perform(
					put("/api/user")
					.contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(userToUpd)))
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isNotFound());
	}
	
	@Test
	@Order(4)
	@DisplayName("GET /api/user/{id}")
	public void getUserById() throws Exception {
		// Mock request
		this.mockMvc
			.perform(
					get("/api/user/" + user.getId())
					)
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			// Validate returned properties
			.andExpect(jsonPath("$.id", is(notNullValue())))
            .andExpect(jsonPath("$.name", is(user.getName())))
            .andExpect(jsonPath("$.email", is(user.getEmail())))
            .andExpect(jsonPath("$.birthDate", is(user.getBirthDate())))
            .andExpect(jsonPath("$.address.id", is(notNullValue())))
            .andExpect(jsonPath("$.address.street", is(user.getAddress().getStreet())))
            .andExpect(jsonPath("$.address.number", is(user.getAddress().getNumber())));
	}
	
	@Test
	@Order(5)
	@DisplayName("GET /api/users/{page}/{size}")
	public void getPagedUsers() throws Exception {
		Integer pageNumber = 0;
		Integer pageSize = 10;
		
		// Mock request
		this.mockMvc
			.perform(
					get("/api/users/" + pageNumber + "/" + pageSize)
					)
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			// Validate returned properties
			.andExpect(jsonPath("$.pageable.pageNumber", is(pageNumber)))
            .andExpect(jsonPath("$.pageable.pageSize", is(pageSize)))
            .andExpect(jsonPath("$.totalElements", greaterThan(0)));
	}
	
	@Test
	@Order(6)
	@DisplayName("DELETE /api/user/{id}")
	public void deleteUserById() throws Exception {
		// Mock request
		this.mockMvc
			.perform(
					delete("/api/user/" + user.getId())
					)
			
			// Print
			.andDo(print())

			// Validate response code
			.andExpect(status().isOk());
		
		// Check if exists user
		this.mockMvc
			.perform(
					get("/api/user/" + user.getId())
					)
			
			// Print
			.andDo(print())

			// Validate response code and content type
			.andExpect(status().isNotFound());
	}
}
