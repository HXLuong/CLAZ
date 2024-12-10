package com.claz.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.claz.models.Product;
import com.claz.models.Product;
import com.claz.models.Product;
import com.claz.repositories.ProductRepository;
import com.claz.repositories.ProductRepository;
import com.claz.repositories.ProductRepository;
import com.claz.serviceImpls.ProductServiceImpl;
import com.claz.serviceImpls.ProductServiceImpl;
import com.claz.serviceImpls.ProductServiceImpl;
import com.claz.services.ProductService;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductServiceImpl productServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private Product mockProductRequest() {
		Product product = new Product();
		product.setId(3);
		product.setName("Product Name");
		product.setImage("default.jpg");
		product.setPrice(100.0);
		product.setQuantity(10);
		product.setDecription("Product Description");
		product.setDiscount(0.1);
		product.setHot(true);
		product.setPurchases(0);
		product.setTotal_Pay(0.0);
		product.setTotal_Rating(0);
		product.setTotal_Stars(0);
		product.setCreated_at(LocalDateTime.now());
		return product;
	}

	// Create
	@Test
	public void testCreateProduct_CreateNull() {
		assertThrows(Exception.class, () -> {
			Product model = null;
			productServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateProduct_Create() {
		assertThrows(Exception.class, () -> {
			Product model = mockProductRequest();
			productServiceImpl.create(model);
		});
	}

	@Test
	public void testCreateProduct_updateNull() {
		assertThrows(Exception.class, () -> {
			Product model = null;
			productServiceImpl.update(model);
		});
	}

	// Update
	@Test
	public void testUpdateProduct_updateValidate() {
		Product product = new Product();
		product.setId(3);
		product.setName("Product 1 Name");
		product.setImage("default.jpg");
		product.setPrice(100.0);
		product.setQuantity(10);
		product.setDecription("Product Description");
		product.setDiscount(0.1);
		product.setHot(true);
		product.setPurchases(0);
		product.setTotal_Pay(0.0);
		product.setTotal_Rating(0);
		product.setTotal_Stars(0);
		product.setCreated_at(LocalDateTime.now());
		productServiceImpl.update(product);
	}

	// Delete
	@Test
	public void testDeleteProduct_deleteNullId() {
		Integer id = null;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			productServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	@Test
	public void testDeleteProduct_deleteValite() {
		int id = 1;
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			productServiceImpl.delete(id);
		});
		assertEquals("Username cannot be null or empty", exception.getMessage());
	}

	// FindBy
	@Test
	void testFindAllProduct() {
		try {
			List<Product> list = productServiceImpl.findAll();
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
				Optional<Product> list = productServiceImpl.findById(id);
			});
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindByIdWithValidate() {
		try {
			Integer id = 1;
			Optional<Product> list = productServiceImpl.findById(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindSearchProduct
	@Test
	void testSelectByFindBySearchProductWithNull() {
		try {
			String id = null;
			List<Product> list = productServiceImpl.findBySearch(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindBySearchProductWithEmpty() {
		try {
			String id = "";
			List<Product> list = productServiceImpl.findBySearch(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void testSelectByFindBySearchProductWithValidate() {
		try {
			String id = "Sieu nhan bay tren cao";
			List<Product> list = productServiceImpl.findBySearch(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// FindAllCategory
//	@Test
//	void testSelectByFindBySearchCategoryWithNull() {
//		try {
//			Integer id = null;
//			List<Product> list = productServiceImpl.findAllByCategoryId(id);
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//	}

	@Test
	void testSelectByFindBySearchCategoryWithValidate() {
		try {
			Integer id = 1;
			List<Product> list = productServiceImpl.findAllByCategoryId(id);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// GetRevenuePerMonth
	@Test
	void testGetRevenuePerMonth() {
		Map<String, Double> revenueMap = productServiceImpl.getRevenuePerMonth();
		assertNotNull(revenueMap);
		assertTrue(revenueMap.size() > 0);
	}

	// testCountTotalProduct
//	@Test
//	void testCountTotalProduct() {
//		long totalProduct = productServiceImpl.countTotalProduct();
//		assertTrue(totalProduct > 0);
//	}

	// testFindByPrice
	@Test
	void testFindByPrice() {
		double minPrice = 50.0;
		double maxPrice = 150.0;

		List<Product> products = productServiceImpl.findByPrice(minPrice, maxPrice);
		assertNotNull(products);
		for (Product product : products) {
			assertTrue(product.getPrice() >= minPrice && product.getPrice() <= maxPrice);
		}
	}

	// Pageable
	@Test
	void testFindbyDMandSort() {
		int dm = 1;
		Pageable pageable = Pageable.ofSize(10).withPage(0);
		Page<Product> productPage = productServiceImpl.findbyDMandSort(dm, pageable);
		assertNotNull(productPage);
		assertTrue(productPage.getTotalElements() > 0);
	}

	// testFindByPricePage
	@Test
	void testFindByPricePage() {
		double minPrice = 50.0;
		double maxPrice = 150.0;
		Pageable pageable = Pageable.ofSize(10).withPage(0);
		Page<Product> productPage = productServiceImpl.findByPricePage(minPrice, maxPrice, pageable);
		assertNotNull(productPage);
		assertTrue(productPage.getTotalElements() > 0);
	}

//	@Test
//	void testFillbyprice() {
//		// Tạo đối tượng Sort hợp lệ
//		Sort sort = Sort.by(Sort.Order.asc("price"));
//
//		// Lấy danh sách sản phẩm
//		List<Product> products = productServiceImpl.fillbyprice(sort);
//
//		// Kiểm tra danh sách sản phẩm không null
//		assertNotNull(products);
//
//		// In ra số lượng sản phẩm để kiểm tra
//		System.out.println("Số lượng sản phẩm: " + products.size());
//
//		// Kiểm tra danh sách sản phẩm có ít nhất 1 sản phẩm
//		assertTrue("Số lượng sản phẩm phải lớn hơn 0", products.size() > 0);
//	}

	// testFindByHot
	@Test
	void testFindByHot() {
		List<Product> products = productServiceImpl.findByHot();
		assertNotNull(products);
		assertTrue(products.size() > 0);
		for (Product product : products) {
			assertTrue(product.isHot());
		}
	}

	// testFindByBestSeller
	@Test
	void testFindByBestSeller() {
		int purchases = 1000000;
		List<Product> products = productServiceImpl.findByBestSeller(purchases);
		assertNotNull(products);
		assertTrue(products.size() > 0);
		for (Product product : products) {
			assertTrue(product.getPurchases() >= purchases);
		}
	}

//testFindAllByGenreId
	@Test
	void testFindAllByGenreId() {
		int genreId = 3;
		List<Product> products = productServiceImpl.findAllByGenreId(genreId);
		assertNotNull(products);
		assertTrue(products.size() > 0);
	}

//testFindProductsWithDiscount
	@Test
	void testFindProductsWithDiscount() {
		List<Product> products = productServiceImpl.findProductsWithDiscount();
		assertNotNull(products);
		assertTrue(products.size() > 0);
		for (Product product : products) {
			assertTrue(product.getDiscount() > 0);
		}
	}

}