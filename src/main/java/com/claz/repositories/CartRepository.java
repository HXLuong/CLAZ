
package com.claz.repositories;

import com.claz.models.Cart;
import com.claz.models.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	List<Cart> findAllByCustomerUsername(String username);

	Optional<Cart> findByCustomerAndName(Customer customer, String productName);
}
