
package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;
import com.claz.models.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByCustomer(Customer customer);

	@Query("SELECT o FROM Order o WHERE o.customer.username=?1")
	List<Order> findByUsername(String username);

	@Query("SELECT o.created_at, o.paymentMethod, od.id, od.price, od.quantity, od.discount FROM Order o inner join OrderDetail od on o.id = od.order.id where o.customer.username = ?1")
	List<Object[]> findOrderByUsername(String username);

	List<Order> findOrdersByCustomerUsername(String username);

//	@Modifying
//	@Query("UPDATE Order o SET o.orderStatus = :status WHERE o.id = :id")
//	void updateOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);

}
