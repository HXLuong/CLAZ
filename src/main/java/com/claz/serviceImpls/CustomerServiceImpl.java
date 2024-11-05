package com.claz.serviceImpls;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.claz.models.Cart;
import com.claz.models.Comment;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Rating;
import com.claz.models.Reply;
import com.claz.repositories.CartRepository;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.CustomerRepository;
import com.claz.repositories.OrderDetailRepository;
import com.claz.repositories.OrderRepository;
import com.claz.repositories.RatingRepository;
import com.claz.repositories.ReplyRepository;
import com.claz.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerDAO;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private RatingRepository ratingRepository;

	public List<Customer> findAll() {
		return customerDAO.findAll();
	}

	public Customer createAccount(Customer account) {
		return customerDAO.save(account);
	}

	@Override
	public Customer update(Customer customer) {
		return customerDAO.save(customer);
	}

	@Override
	public void delete(String username) {
		List<Order> orders = orderRepository.findByUsername(username);
		for (Order order : orders) {
			List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
			for (OrderDetail orderDetail : orderDetails) {
				orderDetailRepository.delete(orderDetail);
			}
			orderRepository.delete(order);
		}

		List<Cart> carts = cartRepository.findAllByCustomerUsername(username);
		for (Cart cart : carts) {
			cartRepository.delete(cart);
		}

		List<Comment> comments = commentRepository.findByCustomerUsername(username);
		for (Comment comment : comments) {
			List<Reply> replies = replyRepository.findByCommentId(comment.getId());
			for (Reply reply : replies) {
				replyRepository.delete(reply);
			}
			commentRepository.delete(comment);
		}

		List<Rating> ratings = ratingRepository.findByCustomerUsername(username);
		for (Rating rating : ratings) {
			ratingRepository.delete(rating);
		}

		customerDAO.deleteById(username);
	}

	public Customer findByUsername(String username) {
		return customerDAO.findByUsername(username).get();
	}

	public Optional<Customer> getname(String username) {
		return customerDAO.findByUsername(username);
	}

	@Override
	public Optional<Customer> findByEmail(String email) {
		return customerDAO.findByEmail(email);
	}

	@Override
	public long countTotalCustomers() {
		return customerDAO.count();
	}

}
