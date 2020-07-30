package com.shoppingbook.service;

import java.util.List;

import com.shoppingbook.entity.CommentRating;

public interface CommentRatingService {
	List<CommentRating> findAllCmrt();
	List<CommentRating> findByBookId(Long id);
	CommentRating findCommentRatingById(Long id);
	void save(CommentRating cmrt);
	void removeOne(long parseLong);
}
