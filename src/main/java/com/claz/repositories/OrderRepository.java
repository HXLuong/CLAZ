
package com.claz.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;
import com.claz.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByCustomer(Customer customer);

	@Query("SELECT o FROM Order o WHERE o.customer.username=?1")
	List<Order> findByUsername(String username);

	@Query("SELECT o.created_at, o.paymentMethod, od.id, od.price, od.quantity, od.discount FROM Order o inner join OrderDetail od on o.id = od.order.id where o.customer.username = ?1")
	List<Object[]> findOrderByUsername(String username);

	List<Order> findOrdersByCustomerUsername(String username);

	@Query("SELECT o FROM Order o WHERE (:orderId IS NULL OR o.id = :orderId) "
			+ "AND (:amountFrom IS NULL OR o.amount >= :amountFrom) "
			+ "AND (:amountTo IS NULL OR o.amount <= :amountTo) "
			+ "AND (:fromDate IS NULL OR o.created_at >= :fromDate) "
			+ "AND (:toDate IS NULL OR o.created_at <= :toDate)" + "AND o.customer.username = :username")
	List<Order> filterOrders(@Param("orderId") Integer orderId, @Param("amountFrom") Double amountFrom,
			@Param("amountTo") Double amountTo, @Param("fromDate") LocalDateTime fromDate,
			@Param("toDate") LocalDateTime toDate, @Param("username") String username);

	@Query("SELECT o FROM Order o WHERE (:paymentMethod IS NULL OR o.paymentMethod LIKE CONCAT('%', :paymentMethod, '%')) "
			+ "AND (:amountFrom IS NULL OR o.amount >= :amountFrom) "
			+ "AND (:amountTo IS NULL OR o.amount <= :amountTo) "
			+ "AND (:fromDate IS NULL OR o.created_at >= :fromDate) "
			+ "AND (:toDate IS NULL OR o.created_at <= :toDate) " + "AND o.customer.username = :username")
	List<Order> filterPayments(@Param("paymentMethod") String paymentMethod, @Param("amountFrom") Double amountFrom,
			@Param("amountTo") Double amountTo, @Param("fromDate") LocalDateTime fromDate,
			@Param("toDate") LocalDateTime toDate, @Param("username") String username);

	@Query("SELECT o FROM Order o WHERE o.created_at > :created_at")
	List<Order> findByCreatedAtAfter(@Param("created_at") LocalDateTime created_at);

	@Query("SELECT COUNT(o) FROM Order o WHERE o.created_at > :created_at")
	Integer countByCreatedAtAfter(LocalDateTime created_at);

	@Query("SELECT o.customer, COUNT(o) FROM Order o GROUP BY o.customer")
	List<Object[]> findAllCustomersWithOrderCount();

	@Query("SELECT o FROM Order o WHERE " + "(CAST(o.id AS string) LIKE %:keyword% OR " + "o.status LIKE %:keyword% OR "
			+ "o.paymentMethod LIKE %:keyword% OR " + "o.customer.fullname LIKE %:keyword% OR "
			+ "CAST(o.amount AS string) LIKE %:keyword%) ORDER BY o.created_at DESC")
	Page<Order> searchOrders(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT o FROM Order o ORDER BY o.created_at DESC")
	Page<Order> findAllOrdersSorted(Pageable pageable);

	@Query("SELECT new map(od.product.id as id, od.product.Name as product, COUNT(od) as totalRevenue) "
			+ "FROM OrderDetail od WHERE YEAR(od.order.created_at) = :year "
			+ "AND (:month IS NULL OR MONTH(od.order.created_at) = :month) "
			+ "GROUP BY od.product.id, od.product.Name")
	List<Map<String, Object>> getProductRevenue(@Param("year") int year, @Param("month") Integer month);

	@Query("SELECT MONTH(o.created_at) as month, SUM(o.amount / 100) as totalRevenue "
			+ "FROM Order o WHERE YEAR(o.created_at) = :year GROUP BY MONTH(o.created_at)")
	List<Map<String, Object>> getMonthlyRevenueByYear(@Param("year") int year);

}
