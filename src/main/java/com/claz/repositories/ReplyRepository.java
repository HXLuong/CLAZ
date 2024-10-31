
package com.claz.repositories;

import com.claz.models.Comment;
import com.claz.models.Reply;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	Optional<Reply> findById(Integer id);

	List<Reply> findByCommentId(int commentId);
	
	@Query("SELECT c FROM Reply c WHERE c.customer.username=?1")
	List<Reply> findByCustomerUsername(String username);
}
