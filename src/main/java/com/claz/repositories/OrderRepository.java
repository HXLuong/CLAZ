
package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;
import com.claz.models.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByCustomer(Customer customer);

	@Query("SELECT o FROM Order o WHERE o.customer.username=?1")
	List<Order> findByUsername(String username);

//	@Modifying
//	@Query("UPDATE Order o SET o.orderStatus = :status WHERE o.id = :id")
//	void updateOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);
}
