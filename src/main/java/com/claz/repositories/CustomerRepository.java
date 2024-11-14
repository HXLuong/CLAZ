package com.claz.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	Optional<Customer> findByUsername(String username);

	@Query("select o from Customer o where o.fullname like ?1")
	Customer handelFindByUserName(Customer username);

	@Query("select o from Customer o where o.email like ?1")
	Optional<Customer> findByEmail(String username);

	@Query("select o from Customer o where o.username = ?1 or o.email = ?1")
	Customer findByUsernameOrEmail(String usernameOrEmail);

	long count();

	@Query("SELECT MONTH(c.created_at) AS month, COUNT(c) AS total FROM Customer c WHERE YEAR(c.created_at) = 2024 GROUP BY month ORDER BY month")
	List<Map<String, Object>> getMonthlyUserRegistrations();

}