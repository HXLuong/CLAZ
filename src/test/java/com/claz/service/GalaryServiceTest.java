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
import com.claz.models.Galary;
import com.claz.models.Product;
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.GalaryRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.GalaryServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class GalaryServiceTest {

	@Autowired
	GalaryRepository galaryRepository;

	@Autowired
	GalaryServiceImpl galaryServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Galary mockGalaryData() {
		Galary galary = new Galary();
		Product product = new Product();
		galary.setId(3);
		galary.setImage("sample-image.jpg");

		product.setId(3);
		galary.setProduct(product);
		return galary;
	}

	// Create
	@Test
	public void testCreateGalary_CreateNull() {
		assertThrows(Exception.class, () -> {
			Galary model = null;
			galaryServiceImpl.create(null);
		});
	}

	@Test
	public void testCreateGalary_Create() {
		assertThrows(Exception.class, () -> {
			Galary model = mockGalaryData();
			galaryServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateGalary_updateNull() {
		assertThrows(Exception.class, () -> {
			Galary model = null;
			galaryServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCategory_updateValidate() {
		Galary galary = new Galary();
		Product product = new Product();
		galary.setId(1);
		galary.setImage("sample-sssmmmmmmmmsssimage.jpg");

		product.setId(3);
		galary.setProduct(product);

		galaryServiceImpl.update(galary);
	}

	// Delete
	@Test
	public void testDeleteCategory_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			galaryServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCategory_deleteValite() {
		int id = 2;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			galaryServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	void testFindAllCategory() {
		try {
			List<Galary> list = galaryServiceImpl.findAll();
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
				Optional<Galary> list = galaryServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<Galary> list = galaryServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindByAllProductId
	@Test
	void testSelectByFindByIAllProductIddWithNull() {
		try {
			Integer id = null;
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				List<Galary> list = galaryServiceImpl.findAllByproductId(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdAllProductIdWithValidate() {
		try {
			Integer id = 3;
			List<Galary> list = galaryServiceImpl.findAllByproductId(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}