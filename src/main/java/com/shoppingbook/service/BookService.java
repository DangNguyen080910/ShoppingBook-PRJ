package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import com.shoppingbook.entity.Book;

public interface BookService {
	Book save(Book book);
	List<Book> findAllBooks();
	Optional<Book> findOne(Long id);
	Book findBookById(Long id);
	//find by name
	List<Book> findBookByName(String bookTitle);
	void removeOne(long parseLong);
//	List<Book> findByCategory(String category);
	List<Book> findByCategoryId(Long id);
	public Long countBook(boolean active);
}
