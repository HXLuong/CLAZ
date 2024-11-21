package com.claz.serviceImpls;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.models.Rating;
import com.claz.models.RatingDTO;
import com.claz.repositories.OrderRepository;
import com.claz.repositories.ProductRepository;
import com.claz.repositories.RatingRepository;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;
import com.claz.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	ProductService productService;

	@Autowired
	CustomerService customerService;

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Rating save(RatingDTO ratingDTO) {
		Integer productId = (Integer) httpSession.getAttribute("productId");
		Product product = productService.finById(productId);

		Customer customer = customerService.findByUsername(ratingDTO.getUsername());

		Rating rating = new Rating();
		rating.setId(ratingDTO.getId());
		rating.setNumber_Stars(ratingDTO.getNumberStars());
		rating.setProduct(product);
		rating.setCustomer(customer);

		return ratingRepository.save(rating);
	}

	@Override
	public Rating update(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public Optional<Rating> findById(int id) {
		return ratingRepository.findById(id);
	}

	@Override
	public List<Rating> findByProductId(int productId) {
		return ratingRepository.findByProductId(productId);
	}

	@Override
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	public boolean hasPurchasedProduct(Integer productId, String username) {
		List<Order> orders = orderRepository.findByUsername(username);
		for (Order order : orders) {
			for (OrderDetail orderDetail : order.getOrderDetails()) {
				if (orderDetail.getProduct().getId() == productId) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasUserRatedProduct(String username, int productId) {
		List<Object[]> productCounts = orderRepository.findProductCountByUsername(username);
		int purchasedQuantity = 0;
		for (Object[] productCount : productCounts) {
			int id = (int) productCount[0]; 
			long count = (long) productCount[1];

			if (id == productId) {
				purchasedQuantity = (int) count;
				break;
			}
		}

		List<Rating> ratings = ratingRepository.findByProductIdAndCustomerUsername(productId, username);

		if (ratings.size() < purchasedQuantity) {
			return false;
		}
		return true;
	}
}
