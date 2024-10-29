package com.claz.serviceImpls;

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
import com.claz.repositories.replyRepository;
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
	private replyRepository replyRepository;

	@Autowired
	private HttpSession httpSession;

	@Override
	public Comment create(CommentDTO commentDTO) {
		Integer productId = (Integer) httpSession.getAttribute("productId");
		Product product = productService.finById(productId);

		Customer customer = customerService.findByUsername(commentDTO.getUsername());

		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setProduct(product);
		comment.setCustomer(customer);

		return commentRepository.save(comment);
	}

	@Override
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
}
