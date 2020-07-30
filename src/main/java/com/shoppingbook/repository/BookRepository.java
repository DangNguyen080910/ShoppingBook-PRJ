package com.shoppingbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	@Query("select b from Book b where b.id =:id")
	Book findBookById(@Param("id") Long id);
	
	@Query("select count(id) from Book where active = :active")
	public Long count(@Param("active") boolean active);

//	List<Book> findByCategory(String category);
	List<Book> findByCategoryId(Long id);
	
	List<Book> findByTitle(String bookTitle);

}
