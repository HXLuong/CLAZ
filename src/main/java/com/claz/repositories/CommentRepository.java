package com.claz.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.claz.models.Comment;
import com.claz.models.CommentDTO;
import com.claz.models.Order;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByProductId(int productId);

	Comment save(CommentDTO comment);

	List<Comment> findByProductId(Integer productId);

	Optional<Comment> findById(Integer id);

	@Query("SELECT c FROM Comment c WHERE c.customer.username=?1")
	List<Comment> findByCustomerUsername(String username);

	@Query("SELECT c FROM Comment c WHERE (:content IS NULL OR c.content LIKE CONCAT('%', :content, '%')) "
			+ "AND (:fromDate IS NULL OR c.created_at >= :fromDate) "
			+ "AND (:toDate IS NULL OR c.created_at <= :toDate) " + "AND c.customer.username = :username")
	List<Comment> filterComments(@Param("content") String content, @Param("fromDate") LocalDateTime fromDate,
			@Param("toDate") LocalDateTime toDate, @Param("username") String username);
}
