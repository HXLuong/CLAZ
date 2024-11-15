package com.claz.rest.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Category;
import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.GenreProduct;
import com.claz.models.Product;
import com.claz.models.Reply;
import com.claz.models.ReplyDTO;
import com.claz.models.Staff;
import com.claz.services.CommentService;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;
import com.claz.services.StaffService;

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

	@Autowired
	StaffService staffService;

	@GetMapping()
	public List<Comment> getAll() {
		return commentService.findAll();
	}

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

	@GetMapping("{id}")
	public ResponseEntity<Comment> getOne(@PathVariable("id") int id) {
		Optional<Comment> optionalComment = commentService.findById(id);
		if (optionalComment.isPresent()) {
			return ResponseEntity.ok(optionalComment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody CommentDTO commentDTO) {
		Optional<Comment> optionalComment = commentService.findById(id);
		Comment existingComment = optionalComment.get();
		existingComment.setContent(commentDTO.getContent());
		commentService.update(existingComment);

		return new ResponseEntity<>(existingComment, HttpStatus.OK);
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
		reply.setId(replyDTO.getId());

		Reply savedReply = commentService.saveReply(reply);

		return ResponseEntity.ok(savedReply);
	}

	@PostMapping("/{commentId}/Staffreplies")
	public ResponseEntity<Reply> addStaffReply(@PathVariable int commentId, @RequestBody ReplyDTO replyDTO) {
		Comment comment = new Comment();
		comment.setId(commentId);
		Reply reply = new Reply();
		reply.setContent(replyDTO.getContent());

		Staff staff = staffService.GetUsername(replyDTO.getUsername_staff());
		reply.setStaff(staff);
		reply.setComment(comment);
		reply.setId(replyDTO.getId());

		Reply savedReply = commentService.saveReply(reply);

		return ResponseEntity.ok(savedReply);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		List<Reply> replies = commentService.getReplies(id);

		for (Reply reply : replies) {
			commentService.deleteReply(reply.getId());
		}
		commentService.delete(id);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/replies/{id}")
	public ResponseEntity<?> updateReply(@PathVariable("id") int id, @RequestBody ReplyDTO replyDTO) {
		Optional<Reply> optionalReply = commentService.findByIdReply(id);
		if (!optionalReply.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Reply existingReply = optionalReply.get();
		existingReply.setContent(replyDTO.getContent());
		commentService.updateReply(existingReply);

		return ResponseEntity.ok(existingReply);
	}

	@DeleteMapping("/replies/{id}")
	public ResponseEntity<?> deleteReply(@PathVariable("id") int id) {
		try {
			Optional<Reply> reply = commentService.findByIdReply(id);
			if (reply != null) {
				commentService.deleteReply(id);
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reply not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting reply");
		}
	}

}
