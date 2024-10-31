package com.claz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	@Query("SELECT r FROM Rating r WHERE r.customer.username=?1")
	List<Rating> findByCustomerUsername(String username);
}
