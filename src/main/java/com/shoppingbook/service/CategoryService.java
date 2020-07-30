package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import com.shoppingbook.entity.Category;

public interface CategoryService {
	
	Category save(Category category);
	List<Category> findAllCategories();
	Optional<Category> findOne(Long id);
	Category findCategoryById(Long id);
	Category findByCategory(String category);
	void removeOne(long parseLong);
}
