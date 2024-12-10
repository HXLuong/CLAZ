package com.claz.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.claz.models.Rating;
import com.claz.models.RatingDTO;

@Service
public interface RatingService {
	Rating save(RatingDTO ratingDTO);

	Rating update(Rating rating);

	Optional<Rating> findById(int id);

	List<Rating> findByProductId(int productId);

	List<Rating> findAll();

	boolean hasPurchasedProduct(Integer productId, String username);

	boolean hasUserRatedProduct(String username, int productId);
}
