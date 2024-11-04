
package com.claz.repositories;

import com.claz.models.Comment;
import com.claz.models.Reply;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	Optional<Reply> findById(Integer id);

	List<Reply> findByCommentId(int commentId);

	@Query("SELECT c FROM Reply c WHERE c.customer.username=?1")
	List<Reply> findByCustomerUsername(String username);

	@Query("SELECT c FROM Reply c WHERE (:content IS NULL OR c.content LIKE CONCAT('%', :content, '%')) "
			+ "AND (:fromDate IS NULL OR c.created_at >= :fromDate) "
			+ "AND (:toDate IS NULL OR c.created_at <= :toDate) " + "AND c.customer.username = :username")
	List<Reply> filterReplies(@Param("content") String content, @Param("fromDate") LocalDateTime fromDate,
			@Param("toDate") LocalDateTime toDate, @Param("username") String username);
}
