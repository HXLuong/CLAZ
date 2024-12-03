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
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.claz.models.Category;
import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Product;
import com.claz.models.Rating;
import com.claz.models.RatingDTO;
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.RatingRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.RatingServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class RatingServiceTest {

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	RatingServiceImpl ratingServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Rating mockRatingData() {
		Rating rating = new Rating();
		rating.setId(1);
		rating.setNumber_Stars(5);
		rating.setCreated_at(LocalDateTime.now());

		Product product = new Product();
		product.setId(3);
		rating.setProduct(product);

		Customer customer = new Customer();
		customer.setUsername("user1");
		rating.setCustomer(customer);

		return rating;
	}

	// Create
	@Test
	public void testCreateCategory_CreateNull() {
		assertThrows(Exception.class, () -> {
			RatingDTO model = null;
			ratingServiceImpl.save(model);
		});
	}

//	@Test
//	public void testCreateComment_Create() {
//		assertThrows(Exception.class, () -> {
//			RatingDTO model = mockRatingData();
//			ratingServiceImpl.(model);
//		});
//	}

	@Test
	public void testCreateCategory_updateNull() {
		assertThrows(Exception.class, () -> {
			Rating model = null;
			ratingServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCategory_updateValidate() {
		Rating rating = new Rating();
		rating.setId(1);
		rating.setNumber_Stars(5);
		rating.setCreated_at(LocalDateTime.now());

		Product product = new Product();
		product.setId(3);
		rating.setProduct(product);

		Customer customer = new Customer();
		customer.setUsername("user1");
		rating.setCustomer(customer);

		ratingServiceImpl.update(rating);
	}

	// FindByCategory
	@Test
	void testFindAllCategory() {
		try {
			List<Rating> list = ratingServiceImpl.findAll();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindByProductId
	@Test
	void testSelectByFindByProductIdWithNull() {
		try {
			Integer id = null;
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				List<Rating> list = ratingServiceImpl.findByProductId(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByProductIdWithValidate() {
		try {
			Integer id = 3;
			List<Rating> list = ratingServiceImpl.findByProductId(id);
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
				Optional<Rating> list = ratingServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<Rating> list = ratingServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testHasPurchasedProduct() {
		String username = "testUser";
		Integer productId = 3;
		boolean hasPurchased = ratingServiceImpl.hasPurchasedProduct(productId, username);
		assertEquals(true, hasPurchased);
	}

	@Test
	public void testHasPurchasedProductNull() {
		String username = null;
		Integer productId = null;
		boolean hasPurchased = ratingServiceImpl.hasPurchasedProduct(productId, username);
		assertEquals(true, hasPurchased);
	}

	@Test
	public void testHasUserRatedProduct() {
		String username = "testUser";
		Integer productId = 3;
		boolean hasPurchased = ratingServiceImpl.hasUserRatedProduct(username, productId);
		assertEquals(true, hasPurchased);
	}

	@Test
	public void testhasUserRatedProductNull() {
		try {
			String username = null;
			Integer productId = null;
			boolean hasPurchased = ratingServiceImpl.hasUserRatedProduct(username, productId);
			assertEquals(true, hasPurchased);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}