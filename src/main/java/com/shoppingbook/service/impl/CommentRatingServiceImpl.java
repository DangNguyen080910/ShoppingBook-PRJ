package com.shoppingbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.CommentRating;
import com.shoppingbook.repository.CommentRatingRepository;
import com.shoppingbook.service.CommentRatingService;

@Service
public class CommentRatingServiceImpl implements CommentRatingService{

	@Autowired
	private CommentRatingRepository cmrtRepository;
	
	@Override
	public CommentRating findCommentRatingById(Long id) {
		return cmrtRepository.findCommentRatingById(id);
	}

	@Override
	public List<CommentRating> findAllCmrt() {
		return cmrtRepository.findAll();
	}

	@Override
	public void removeOne(long parseLong) {
		cmrtRepository.deleteById(parseLong);
	}

	@Override
	public void save(CommentRating cmrt) {
		cmrtRepository.save(cmrt);
	}

	@Override
	public List<CommentRating> findByBookId(Long id) {
		List<CommentRating> cmrtList = cmrtRepository.findByBookId(id);
		List<CommentRating> activeCmrtList = new ArrayList<>();
		for (CommentRating cmrt : cmrtList) {			
				activeCmrtList.add(cmrt);
		}
		return activeCmrtList;
	}
	
}
