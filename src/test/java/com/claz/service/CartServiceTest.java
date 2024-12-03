package com.claz.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.claz.models.Cart;
import com.claz.models.Category;
import com.claz.models.Customer;
import com.claz.models.Category;
import com.claz.repositories.CartRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CartServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class CartServiceTest {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CartServiceImpl cartServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	public Cart mockCartRequest() {
//		Customer customer = new Customer();
//		customer.setFullname("ssss");
		Cart cart = new Cart();
		cart.setId(1);
		cart.setName("Product Name");
		cart.setPrice(100.0);
		cart.setQuantity(2);
		cart.setImage("product_image.jpg");
		cart.setProductID(101);
		cart.setDiscount(10.0);
//		cart.setCustomer(String.valueOf("ssss")); // Gán đối tượng Customer
//		 cart.setCustomer(customer);
		return cart;
	}

	// Create
	@Test
	public void testCreateCart_CreateNull() {
		assertThrows(Exception.class, () -> {
			Cart model = null;
			cartServiceImpl.addItemToCart(model);
		});
	}

	@Test
	public void testCreateCart_Create() {
		Exception exception = assertThrows(Exception.class, () -> {
			Cart model = mockCartRequest();
			cartServiceImpl.addItemToCart(model);
		});
		assertEquals("Expected error message", exception.getMessage());
	}

//	public void testDeleteCategory_deleteNullId() {
//		Integer id = null;
//		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//			categoryServiceImpl.delete(id);
//		});
//		assertEquals("Username cannot be null or empty", exception.getMessage());
//	}
//
	@Test
	public void testDeleteCart_deleteValite() {
		int id = 2;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			cartServiceImpl.deleteItemInCart(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindByCategory
	@Test
	void testFindAllCartNull() {
		try {
			String username = null;
			List<Cart> list = cartServiceImpl.getAllItemInCart(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testFindAllCartEmpty() {
		try {
			String username = "";
			List<Cart> list = cartServiceImpl.getAllItemInCart(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testFindAllCategory() {
		try {
			String username = "cc";
			List<Cart> list = cartServiceImpl.getAllItemInCart(username);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindById
	@Test
	void testSelectByFindByIdWithNull() {
		try {
			Integer id = null;
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Cart list = cartServiceImpl.findItemById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Cart list = cartServiceImpl.findItemById(id);
		} catch (NoSuchElementException e) {

		}
	}

}