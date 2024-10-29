package com.claz.rest.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Product;
import com.claz.models.Reply;
import com.claz.models.ReplyDTO;
import com.claz.services.CommentService;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentRestController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	CustomerService customerService;

	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody CommentDTO commentDTO, HttpSession session) {
		try {
			Integer productId = (Integer) session.getAttribute("productId");
			System.err.println(productId);

			commentDTO.setProductId(productId);
			Comment comment = commentService.create(commentDTO);
			return new ResponseEntity<>(comment, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/comments")
	public ResponseEntity<List<Comment>> getCommentsByProductId(HttpSession session) {
		Integer productId = (Integer) session.getAttribute("productId");
		if (productId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Comment> comments = commentService.findByProductId(productId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}


	
	@GetMapping("/{commentId}/replies")
	public ResponseEntity<List<Reply>> getReplies(@PathVariable int commentId) {
	    List<Reply> replies = commentService.getReplies(commentId);
	    return ResponseEntity.ok(replies);
	}


	@PostMapping("/{commentId}/replies")
	public ResponseEntity<Reply> addReply(@PathVariable int commentId, @RequestBody ReplyDTO replyDTO) {
		Comment comment = new Comment();
		comment.setId(commentId);

		Reply reply = new Reply();
		reply.setContent(replyDTO.getContent());

		Customer customer = customerService.findByUsername(replyDTO.getUsername());
		reply.setCustomer(customer);
		reply.setComment(comment);

		Reply savedReply = commentService.saveReply(reply);

		return ResponseEntity.ok(savedReply);
	}

}
