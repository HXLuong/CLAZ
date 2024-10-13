package com.claz.DAO;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, String> {
	Optional<Customer> findByUsername(String name);

	@Query("select o from Customer o where o.fullname like ?1")
	Customer handelFindByUserName(String username);
//	Optional<Account> findByEmail(String email);
//
//	Optional<Account> findByPhone(String phone);
}