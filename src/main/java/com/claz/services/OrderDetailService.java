package com.claz.services;

import java.util.List;

import com.claz.models.OrderDetail;

public interface OrderDetailService {

	void createOrderDetail(OrderDetail orderDetail);

	double totalAmount();

	List<OrderDetail> findAll();

	boolean hasPurchasedProduct(String username, Integer productId);

	List<OrderDetail> findByOrderCustomerUsernameAndProductId(String username, int productId);
}