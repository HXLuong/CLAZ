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
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class CategoryServiceTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryServiceImpl categoryServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Category mockCategoryRequest() {
		Category category = new Category();
		category.setId(11);
		category.setName("Category Name");
		category.setDecription("Category Description");
		return category;
	}

	// Create
	@Test
	public void testCreateCategory_CreateNull() {
		assertThrows(Exception.class, () -> {
			Category model = null;
			categoryServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateCategory_Create() {
		assertThrows(Exception.class, () -> {
			Category model = mockCategoryRequest();
			categoryServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateCategory_updateNull() {
		assertThrows(Exception.class, () -> {
			Category model = null;
			categoryServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCategory_updateValidate() {
		Category category = new Category();
		category.setId(7);
		category.setName("Category Name");
		category.setDecription("Category Descriptionssss");
		categoryServiceImpl.update(category);
	}

	// Delete
	@Test
	public void testDeleteCategory_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			categoryServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCategory_deleteValite() {
		int id = 7;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			categoryServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindByCategory
	@Test
	void testFindAllCategory() {
		try {
			List<Category> list = categoryServiceImpl.findAll();
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
				Optional<Category> list = categoryServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<Category> list = categoryServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}