package com.claz.serviceImpls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Product;
import com.claz.models.Reply;
import com.claz.repositories.CommentRepository;
import com.claz.repositories.ReplyRepository;
import com.claz.services.CommentService;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private HttpSession httpSession;

	@Override
	public Comment create(CommentDTO commentDTO) {
		Integer productId = (Integer) httpSession.getAttribute("productId");
		Product product = productService.finById(productId);

		Customer customer = customerService.findByUsername(commentDTO.getUsername());

		Comment comment = new Comment();
		comment.setId(commentDTO.getId());
		comment.setContent(commentDTO.getContent());
		comment.setProduct(product);
		comment.setCustomer(customer);

		return commentRepository.save(comment);
	}

	public Comment update(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> findByProductId(Integer productId) {
		return commentRepository.findByProductId(productId);
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Reply saveReply(Reply reply) {
		return replyRepository.save(reply);
	}

	@Override
	public List<Reply> getRepliesByCommentId(Integer commentId) {
		return replyRepository.findByCommentId(commentId);
	}

	@Override
	public List<Reply> getReplies(int commentId) {
		return replyRepository.findByCommentId(commentId);
	}

	@Override
	public Optional<Comment> findById(int id) {
		return commentRepository.findById(id);
	}

	public void delete(int id) {
		commentRepository.deleteById(id);
	}

	public void deleteReply(int id) {
		replyRepository.deleteById(id);
	}

	@Override
	public Reply updateReply(Reply Reply) {
		return replyRepository.save(Reply);
	}

	@Override
	public Optional<Reply> findByIdReply(int id) {
		return replyRepository.findById(id);
	}

	@Override
	public List<Comment> findByUsername(String username) {
		return commentRepository.findByCustomerUsername(username);
	}

	@Override
	public List<Reply> findByUsernameReply(String username) {
		return replyRepository.findByCustomerUsername(username);
	}

	@Override
	public List<Comment> filterComments(String content, LocalDateTime fromDate, LocalDateTime toDate, String username) {
		return commentRepository.filterComments(content, fromDate, toDate, username);
	}

	@Override
	public List<Reply> filterReplies(String content, LocalDateTime fromDate, LocalDateTime toDate, String username) {
		return replyRepository.filterReplies(content, fromDate, toDate, username);
	}

}
