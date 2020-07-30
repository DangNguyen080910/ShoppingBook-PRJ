package com.shoppingbook.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Category;
import com.shoppingbook.repository.CategoryRepository;
import com.shoppingbook.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().sorted(Comparator.comparing(Category::getId).reversed()).collect(Collectors.toList());
	}

	@Override
	public Optional<Category> findOne(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category findCategoryById(Long id) {
		return categoryRepository.findCategoryById(id);
	}

	@Override
	public void removeOne(long parseLong) {
		categoryRepository.deleteById(parseLong);
		
	}

	@Override
	public Category findByCategory(String category) {
		return categoryRepository.findByCategory(category);
	}


}
