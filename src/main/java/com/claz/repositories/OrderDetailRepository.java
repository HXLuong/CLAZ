
package com.claz.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	boolean existsByOrderCustomerUsernameAndProductId(String username, int productId);

	List<OrderDetail> findByOrderCustomerUsernameAndProductId(String username, int productId);

	@Query("SELECT od FROM OrderDetail od WHERE od.order.id=?1")
	List<OrderDetail> findByOrderId(int id);

	@Query("SELECT od FROM OrderDetail od WHERE od.product.id=?1")
	List<OrderDetail> findByProductId(int id);

	@Query("SELECT new map(od.product.Name as product, COUNT(od) as totalRevenue) "
			+ "FROM OrderDetail od WHERE MONTH(od.order.created_at) = 11 AND YEAR(od.order.created_at) = 2024 "
			+ "GROUP BY od.product.Name")
	List<Map<String, Object>> getProductRevenue();

	@Query("SELECT new map(MONTH(od.order.created_at) as month, SUM(od.price * (1 - od.product.Discount / 100)) as totalRevenue) "
			+ "FROM OrderDetail od WHERE YEAR(od.order.created_at) = :year GROUP BY MONTH(od.order.created_at)")
	List<Map<String, Object>> getMonthlyRevenueByYear(@Param("year") int year);

}