
package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;
import com.claz.models.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByCustomer(Customer customer);

	@Query("SELECT o FROM Order o WHERE o.customer.username=?1")
	List<Order> findByUsername(String username);

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
			+ "CAST(o.amount AS string) LIKE %:keyword%)")
	List<Order> searchOrders(@Param("keyword") String keyword);
}
