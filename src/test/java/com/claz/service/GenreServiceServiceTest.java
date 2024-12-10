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
import com.claz.repositories.GenreRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.GalaryServiceImpl;
import com.claz.serviceImpls.GenreProductServiceImpl;
import com.claz.serviceImpls.GenreServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class GenreServiceServiceTest {

	@Autowired
	GenreRepository genreRepository;

	@Autowired
	GenreServiceImpl genreServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Genre mockGenreData() {
		Genre genre = new Genre();
		genre.setId(363303);
		genre.setName("Sample Genre");
		genre.setDecription("This is a sample genre description.");
		return genre;
	}

	// Create
	@Test
	public void testCreateGalary_CreateNull() {
		assertThrows(Exception.class, () -> {
			Genre model = null;
			genreServiceImpl.create(null);
		});
	}

	@Test
	public void testCreateGalary_Create() {
		assertThrows(Exception.class, () -> {
			Genre model = mockGenreData();
			genreServiceImpl.create(model);
		});
	}

//	@Test
	public void testCreateGalary_updateNull() {
		assertThrows(Exception.class, () -> {
			Genre model = null;
			genreServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateGenreProduct_updateValidate() {
		Genre genre = new Genre();
		genre.setId(363303);
		genre.setName("Sample Genre");
		genre.setDecription("This is a sample genre description.");
		genreServiceImpl.update(genre);
	}

	// Delete
	@Test
	public void testDeleteGenreProduct_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			genreServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteGenreProduct_deleteValite() {
		int id = 943751;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			genreServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	void testFindAllCategory() {
		try {
			List<Genre> list = genreServiceImpl.findAll();
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
				Optional<Genre> list = genreServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 3;
			Optional<Genre> list = genreServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
