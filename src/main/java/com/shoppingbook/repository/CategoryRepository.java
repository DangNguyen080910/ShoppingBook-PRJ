package com.shoppingbook.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	@Query("select c from Category c where c.id =:id")
	Category findCategoryById(@Param("id") Long id);
	
	Category findByCategory(String category);
	
	
}
