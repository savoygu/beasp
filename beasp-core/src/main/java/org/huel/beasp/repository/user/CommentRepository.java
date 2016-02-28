package org.huel.beasp.repository.user;

import org.huel.beasp.entity.user.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment>{
	
	
	public Page<Comment> findByBook_Id(Integer bookId, Pageable pageable);
	
}
