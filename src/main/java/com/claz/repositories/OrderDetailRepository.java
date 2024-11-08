
package com.claz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claz.models.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	boolean existsByOrderCustomerUsernameAndProductId(String username, int productId);

	List<OrderDetail> findByOrderCustomerUsernameAndProductId(String username, int productId);

}
