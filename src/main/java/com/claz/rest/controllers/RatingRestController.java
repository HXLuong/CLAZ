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
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Rating;
import com.claz.models.RatingDTO;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
import com.claz.services.RatingService;

@RestController
@CrossOrigin
@RequestMapping("/ratings")
public class RatingRestController {

	@Autowired
	private RatingService ratingService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	OrderService orderService;

	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody RatingDTO rating, HttpSession session) {
		Rating savedRating = ratingService.save(rating);
		session.setAttribute("hasRated", true);

		Map<String, Object> response = new HashMap<>();
		response.put("hasRated", true);
		response.put("rating", savedRating);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
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
			response.put("canRateAgain", false);
			return ResponseEntity.badRequest().body(response);
		}

		// Kiểm tra xem người dùng đã thanh toán cho sản phẩm này chưa
		boolean hasPurchased = orderDetailService.hasPurchasedProduct(username, productId);
		boolean canRateAgain = hasPurchased; // Người dùng đã thanh toán thì có thể đánh giá

		response.put("canRateAgain", canRateAgain);

		session.setAttribute("canRateAgain", canRateAgain);
		return ResponseEntity.ok(response);
	}
}
