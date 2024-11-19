package com.claz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Rating;
import com.claz.models.RatingDTO;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	Rating save(RatingDTO RatingDTO);

	List<Rating> findByProductId(int productId);

	List<Rating> findByProductIdAndCustomerUsername(int productId, String username);

	boolean existsByCustomerUsernameAndProductId(String username, Integer productId);
	
	@Query("SELECT r FROM Rating r WHERE r.customer.username=?1")
	List<Rating> findByCustomerUsername(String username);
}
