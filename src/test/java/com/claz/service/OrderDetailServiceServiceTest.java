package com.claz.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.claz.models.Category;
import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Galary;
import com.claz.models.Genre;
import com.claz.models.GenreProduct;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.models.Category;
import com.claz.repositories.CategoryRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.GalaryRepository;
import com.claz.repositories.GenreProductRepository;
import com.claz.repositories.OrderDetailRepository;
import com.claz.repositories.CategoryRepository;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.serviceImpls.CommentServiceImpl;
import com.claz.serviceImpls.GalaryServiceImpl;
import com.claz.serviceImpls.GenreProductServiceImpl;
import com.claz.serviceImpls.OrderDetailServiceImpl;
import com.claz.serviceImpls.CategoryServiceImpl;
import com.claz.services.CategoryService;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OrderDetailServiceServiceTest {

	@Autowired
	OrderDetailRepository detailRepository;

	@Autowired
	OrderDetailServiceImpl detailServiceImpl;

	@AfterClass
	void tearDown() throws Exception {
	}

	private OrderDetail mockOrderDetailData() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(1);
		orderDetail.setPrice(200.0);
		orderDetail.setQuantity(2);
		orderDetail.setDiscount(10.0);
		orderDetail.setKeyProduct("KEY123");
		Product product = new Product();
		product.setId(3);
		orderDetail.setProduct(product);
		Order order = new Order();
		order.setId(312180);
		orderDetail.setOrder(order);

		return orderDetail;
	}

	// Create
	@Test
	public void testCreateOrderDetail_CreateNull() {
		assertThrows(Exception.class, () -> {
			OrderDetail model = null;
			detailServiceImpl.createOrderDetail(model);
		});
	}

	@Test
	public void testCreateOrderDetail_Create() {
		assertThrows(Exception.class, () -> {
			OrderDetail model = mockOrderDetailData();
			detailServiceImpl.createOrderDetail(model);
		});
	}

	@Test
	void testTotalAmountEqualsExpected() {
		List<OrderDetail> mockOrderDetails = new ArrayList<>();
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setPrice(300000.0);
		orderDetail1.setQuantity(1);
		orderDetail1.setDiscount(0.0);
		mockOrderDetails.add(orderDetail1);

		OrderDetail orderDetail2 = new OrderDetail();
		orderDetail2.setPrice(200000.0);
		orderDetail2.setQuantity(1);
		orderDetail2.setDiscount(0.0);
		mockOrderDetails.add(orderDetail2);

		double totalAmount = detailServiceImpl.totalAmount();

		assertEquals(500000.0, totalAmount, 0.0);

	}

	@Test
	public void testHasPurchasedProduct() {
		String username = "testUser";
		Integer productId = 3;
		boolean hasPurchased = detailServiceImpl.hasPurchasedProduct(username, productId);
		assertEquals(true, hasPurchased);
	}

	@Test
	public void testHasPurchasedProductNull() {
		try {
			String username = null;
			int productId = (Integer) null;
			boolean hasPurchased = detailServiceImpl.hasPurchasedProduct(username, productId);
			assertEquals(true, hasPurchased);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	void testFindAllOrderDetail() {
		try {
			List<OrderDetail> list = detailServiceImpl.findAll();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
}
