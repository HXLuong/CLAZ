
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

	@Query("SELECT new map(FUNCTION('DATE', o.created_at) AS date, SUM(od.price * od.quantity * (1 - od.discount)) AS dailyRevenue) "
			+ "FROM OrderDetail od JOIN od.order o " + "WHERE YEAR(o.created_at) = :year "
			+ "GROUP BY FUNCTION('DATE', o.created_at) " + "ORDER BY date")
	List<Map<String, Object>> findDailyRevenueByYear(@Param("year") int year);
}
