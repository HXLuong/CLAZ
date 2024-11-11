package com.claz.rest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Rating;
import com.claz.models.RatingDTO;
import com.claz.services.OrderDetailService;
import com.claz.services.RatingService;

@RestController
@CrossOrigin
@RequestMapping("/ratings")
public class RatingRestController {

	@Autowired
	private RatingService ratingService;

	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody RatingDTO rating) {
		try {
			Rating savedRating = ratingService.save(rating);
			return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody RatingDTO dto) {
		Optional<Rating> optionalRating = ratingService.findById(id);

		Rating existingRating = optionalRating.get();
		existingRating.setNumber_Stars(dto.getNumberStars());
		ratingService.update(existingRating);

		return new ResponseEntity<>(existingRating, HttpStatus.OK);
	}

	@GetMapping()
	public List<Rating> findAll() {
		return ratingService.findAll();
	}

	@GetMapping("/rating")
	public ResponseEntity<List<Rating>> getCommentsByProductId(HttpSession session) {
		Integer productId = (Integer) session.getAttribute("productId");

		if (productId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Rating> ratings = ratingService.findByProductId(productId);

		if (ratings.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(ratings, HttpStatus.OK);
	}

//	@GetMapping("/check")
//	public ResponseEntity<Map<String, Boolean>> checkPurchase(@RequestParam("productId") Long productId,
//			@RequestParam("username") String username) {
//
//		boolean hasPurchased = ratingService.hasPurchasedProduct(productId, username);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("hasPurchased", hasPurchased);
//
//		return ResponseEntity.ok(response); 
//	}
	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("/checkPurchaseStatus")
	public ResponseEntity<Boolean> checkPurchaseStatus(@RequestParam String username, HttpSession session) {
		Integer productId = (Integer) session.getAttribute("productId");

		boolean hasPurchased = orderDetailService.hasPurchasedProduct(username, productId);
		return ResponseEntity.ok(hasPurchased);
	}

	@GetMapping("/checkIfRated")
	public ResponseEntity<Map<String, Boolean>> checkIfRated(@RequestParam String username, HttpSession session) {
	    Integer productId = (Integer) session.getAttribute("productId");

	    Map<String, Boolean> response = new HashMap<>();

	    if (productId == null) {
	        response.put("hasRated", false);
	        response.put("canRateAgain", false);
	        return ResponseEntity.badRequest().body(response);
	    }

	    boolean hasRated = ratingService.hasUserRatedProduct(username, productId);
	    boolean hasPurchased = orderDetailService.hasPurchasedProduct(username, productId);

	    // Đặt canRateAgain = true nếu người dùng đã mua sản phẩm nhưng chưa đánh giá
	    boolean canRateAgain = hasPurchased && !hasRated;
	    session.setAttribute("hasRated", hasRated);
	    session.setAttribute("canRateAgain", canRateAgain);

	    response.put("hasRated", hasRated);
	    response.put("canRateAgain", canRateAgain);

	    return ResponseEntity.ok(response);
	}
}
