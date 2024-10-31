package com.claz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByProductId(int productId);

	Comment save(CommentDTO comment);

	List<Comment> findByProductId(Integer productId);

	Optional<Comment> findById(Integer id);
	
	@Query("SELECT c FROM Comment c WHERE c.customer.username=?1")
	List<Comment> findByCustomerUsername(String username);
}
