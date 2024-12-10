package com.claz.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.claz.models.Customer;
import com.claz.repositories.CustomerRepository;
import com.claz.serviceImpls.CustomerServiceImpl;
import com.claz.services.CustomerService;

@SpringBootTest
public class CustomerServiceTest {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerServiceImpl customerService;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Customer mockCustomerRequest() {
		Customer customer = new Customer();
		customer.setUsername("123");
		customer.setFullname("Test User");
		customer.setPassword("password123");
		customer.setEmail("testuser@example.com");
		customer.setPhone("1234567890");
		customer.setImage("default.jpg");
		customer.setGender(true);
		customer.setCreated_at(LocalDateTime.now());
		return customer;
	}

	// Create
	@Test
	public void testCreateCustomer_CreateNull() {
		assertThrows(Exception.class, () -> {
			Customer model = null;
			customerService.createAccount(model);
		});
	}

	@Test
	public void testCreateCustomer_Create() {
		assertThrows(Exception.class, () -> {
			Customer model = mockCustomerRequest();
			customerService.createAccount(model);
		});
	}

	@Test
	public void testCreateCustomer_updateNull() {
		assertThrows(Exception.class, () -> {
			Customer model = null;
			customerService.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCustomer_updateValidate() {
		Customer model = new Customer();
		model.setUsername("mmmmmm");
		model.setFullname("Test User");
		model.setPassword("password123");
		model.setEmail("testuser@example.com");
		model.setPhone("1234567890");
		model.setImage("defaultsss.jpg");
		model.setGender(true);
		model.setCreated_at(LocalDateTime.now());
		customerService.update(model);
	}

//	@Test
//	public void testUpdateCustomer_WithEmptyUsername() {
//		Customer model = new Customer();
//		model.setUsername("");
//		model.setFullname("Test User");
//		model.setPassword("password123");
//		model.setEmail("testuser@example.com");
//		model.setPhone("1234567890");
//		model.setImage("default.jpg");
//		model.setGender(true);
//		model.setCreated_at(LocalDateTime.now());
//
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			customerService.update(model);
//		});
//
//		assertEquals("Username cannot be null or empty", exception.getMessage());
//	}

	// Delete
	@Test
	public void testDeleteCustomer_deleteEmptyId() {
		String username = "";
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			customerService.delete(username);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCustomer_deleteNullId() {
		String username = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			customerService.delete(username);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCustomer_deleteValite() {
		String username = "123";
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			customerService.delete(username);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindCustomer
	@Test
	void testFindAllCustomer() {
		try {
			List<Customer> list = customerService.findAll();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByfindByUsernameWithEmpty() {
		try {
			String username = "";
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByfindByUsernameWithNull() {
		try {
			String username = null;
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByfindByUsernameWithValidate() {
		try {
			String username = "123";
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// GetName
	@Test
	void testSelectByGetNameWithEmpty() {
		try {
			String username = "";
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByGetNameWithNull() {
		try {
			String username = null;
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByGetNameWithValidate() {
		try {
			String username = "123";
			Optional<Customer> list = customerService.getname(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindByEmail
	@Test
	void testSelectByFindByEmailWithEmpty() {
		try {
			String email = "";
			Optional<Customer> list = customerService.getname(email);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByEmailWithNull() {
		try {
			String email = null;
			Optional<Customer> list = customerService.getname(email);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByEmailWithValidate() {
		try {
			String email = "leminhc@example.com";
			Optional<Customer> list = customerService.getname(email);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

//	@Test
//	void testFindByUsernameCustomer() {
//		try {
//			List<Customer> list = customerService.findAll();
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//	}

//	@Test
//	public void testCreateCustomer_WhenUsernameDoesNotExist_ShouldCreate() {
//		Customer mockRequest = mockCustomerRequest();
//		Mockito.when(customerRepository.existsById(mockRequest.getUsername())).thenReturn(false);
//		Mockito.when(customerRepository.save(mockRequest)).thenReturn(mockRequest);
//
//		Customer result = customerService.createAccount(mockRequest);
//
//		assertNotNull(result);
//		verify(customerRepository).save(mockRequest);
//	}

}