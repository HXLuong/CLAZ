
package com.claz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
<<<<<<< HEAD
	boolean existsByOrderCustomerUsernameAndProductId(String username, int productId);

	List<OrderDetail> findByOrderCustomerUsernameAndProductId(String username, int productId);

=======
	@Query("SELECT od FROM OrderDetail od WHERE od.order.id=?1")
	List<OrderDetail> findByOrderId(int id);
	
	@Query("SELECT od FROM OrderDetail od WHERE od.product.id=?1")
	List<OrderDetail> findByProductId(int id);
>>>>>>> abb2f9bd68225994a1d661327f725013cc17c6ac
}
