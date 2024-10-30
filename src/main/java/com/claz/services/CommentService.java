package com.claz.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Customer;
import com.claz.models.Product;
import com.claz.models.Reply;

public interface CommentService {
	Comment create(CommentDTO commentDTO);

	Comment update(Comment Comment);

	List<Comment> findByProductId(Integer productId);

	List<Comment> findAll();

	Reply saveReply(Reply reply);

	List<Reply> getRepliesByCommentId(Integer commentId);

	List<Reply> getReplies(int commentId);

	Optional<Comment> findById(int id);

	void delete(int id);

	void deleteReply(int id);

	Reply updateReply(Reply Reply);

	Optional<Reply> findByIdReply(int id);
}
