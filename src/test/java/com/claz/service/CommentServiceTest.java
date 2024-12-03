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
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	CommentServiceImpl commentServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private CommentDTO mockCommentRequest() {
		CommentDTO comment = new CommentDTO();
		comment.setId(1);
		comment.setContent("This is a sample comment.");
		comment.setProductId(1);
		comment.setUsername("bin");
		return comment;
	}

	// Create
	@Test
	public void testCreateCategory_CreateNull() {
		assertThrows(Exception.class, () -> {
			Comment model = null;
			commentServiceImpl.create(null);
		});
	}

	@Test
	public void testCreateComment_Create() {
		assertThrows(Exception.class, () -> {
			CommentDTO model = mockCommentRequest();
			commentServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateCategory_updateNull() {
		assertThrows(Exception.class, () -> {
			Comment model = null;
			commentServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCategory_updateValidate() {
		Comment comment = new Comment();
		Product product = new Product();
		Customer customer = new Customer();
		comment.setId(2);
		comment.setContent("This is a sample commentcccccccc.");
		product.setId(3);
		comment.setProduct(product);
		customer.setUsername("user2");
		comment.setCustomer(customer);

		commentServiceImpl.update(comment);
	}

	// Delete
	@Test
	public void testDeleteCategory_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			commentServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCategory_deleteValite() {
		int id = 3;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			commentServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindByCategory
	@Test
	void testFindAllCategory() {
		try {
			List<Comment> list = commentServiceImpl.findAll();
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
				Optional<Comment> list = commentServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<Comment> list = commentServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}