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
import com.claz.models.Genre;
import com.claz.models.GenreProduct;
import com.claz.models.Product;
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.GalaryRepository;
import com.claz.repositories.GenreProductRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.GalaryServiceImpl;
import com.claz.serviceImpls.GenreProductServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class GenreProductServiceServiceTest {

	@Autowired
	GenreProductRepository genreProductRepository;

	@Autowired
	GenreProductServiceImpl genreProductServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private GenreProduct mockGenreProductData() {
		GenreProduct genreProduct = new GenreProduct();

		// Mock Product
		Product product = new Product();
		product.setId(3);

		// Mock Genre
		Genre genre = new Genre();
		genre.setId(363303);

		genreProduct.setId(5);
		genreProduct.setName("Sample Genre Product");
		genreProduct.setProduct(product);
		genreProduct.setGenre(genre);

		return genreProduct;
	}

	// Create
	@Test
	public void testCreateGalary_CreateNull() {
		assertThrows(Exception.class, () -> {
			GenreProduct model = null;
			genreProductServiceImpl.create(null);
		});
	}

	@Test
	public void testCreateGalary_Create() {
		assertThrows(Exception.class, () -> {
			GenreProduct model = mockGenreProductData();
			genreProductServiceImpl.create(model);
		});
	}

//	@Test
	public void testCreateGalary_updateNull() {
		assertThrows(Exception.class, () -> {
			GenreProduct model = null;
			genreProductServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateGenreProduct_updateValidate() {
		GenreProduct genreProduct = new GenreProduct();

		Product product = new Product();
		product.setId(3);

		Genre genre = new Genre();
		genre.setId(363303);

		genreProduct.setId(387792);
		genreProduct.setName("Sample Genre Product");
		genreProduct.setProduct(product);
		genreProduct.setGenre(genre);

		genreProductServiceImpl.update(genreProduct);
	}

	// Delete
	@Test
	public void testDeleteGenreProduct_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			genreProductServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteGenreProduct_deleteValite() {
		int id = 943751;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			genreProductServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	void testFindAllCategory() {
		try {
			List<GenreProduct> list = genreProductServiceImpl.findAll();
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
				Optional<GenreProduct> list = genreProductServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<GenreProduct> list = genreProductServiceImpl.findById(id);
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
				List<GenreProduct> list = genreProductServiceImpl.findAllByproductId(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdAllProductIdWithValidate() {
		try {
			Integer id = 3;
			List<GenreProduct> list = genreProductServiceImpl.findAllByproductId(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
