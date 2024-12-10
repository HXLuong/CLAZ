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
import java.util.Date;
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
import com.claz.models.Slide;
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.SlideRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.SlideServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;

@SpringBootTest
public class SlideServiceTest {

	@Autowired
	SlideRepository slideRepository;

	@Autowired
	SlideServiceImpl slideServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Slide mockSlideRequest() {
		Slide slide = new Slide();
		slide.setId(13);
		slide.setImage("example_slide.jpg");
		slide.setCreated_at(new Date());
		return slide;
	}

	// Create
	@Test
	public void testCreateCategory_CreateNull() {
		assertThrows(Exception.class, () -> {
			Slide model = null;
			slideServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateCategory_Create() {
		assertThrows(Exception.class, () -> {
			Slide model = mockSlideRequest();
			slideServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateCategory_updateNull() {
		assertThrows(Exception.class, () -> {
			Slide model = null;
			slideServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateCategory_updateValidate() {
		Slide slide = new Slide();
		slide.setId(11);
		slide.setImage("examplessss_slide.jpg");
		slide.setCreated_at(new Date());
		slideServiceImpl.update(slide);
	}

	// Delete
	@Test
	public void testDeleteCategory_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			slideServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteCategory_deleteValite() {
		int id = 11;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			slideServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindByCategory
	@Test
	void testFindAllCategory() {
		try {
			List<Slide> list = slideServiceImpl.findAll();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindById
//	@Test
//	void testSelectByFindByIdWithNull() {
//		try {
//			int id = (Integer) null;
//			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//				Optional<Slide> list = slideServiceImpl.findById(id);
//			});
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 13;
			Optional<Slide> list = slideServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}