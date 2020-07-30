package com.shoppingbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.CommentRating;

public interface CommentRatingRepository extends JpaRepository<CommentRating, Long> {
	
	@Query("select c from Book c where c.id =:id")
	CommentRating findCommentRatingById(@Param("id") Long id);	

	List<CommentRating> findByBookId(Long id);
}
