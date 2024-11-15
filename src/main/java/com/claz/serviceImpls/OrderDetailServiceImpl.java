package com.claz.serviceImpls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.OrderDetail;
import com.claz.repositories.OrderDetailRepository;
import com.claz.services.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	public void createOrderDetail(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	@Override
	public double totalAmount() {
		double total = 0;

		List<OrderDetail> orderDetails = orderDetailRepository.findAll();
		for (OrderDetail orderDetail : orderDetails) {
			total += orderDetail.getPrice() * orderDetail.getQuantity();
		}
		return total;
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public boolean hasPurchasedProduct(String username, Integer productId) {
		return orderDetailRepository.existsByOrderCustomerUsernameAndProductId(username, productId);
	}

	@Override
	public List<OrderDetail> findByOrderCustomerUsernameAndProductId(String username, int productId) {
		return orderDetailRepository.findByOrderCustomerUsernameAndProductId(username, productId);
	}
}
