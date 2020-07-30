package com.shoppingbook.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Book;
import com.shoppingbook.repository.BookRepository;
import com.shoppingbook.service.BookService;

	@Service
	public class BookServiceImpl implements BookService{
		
		@Autowired
		private BookRepository bookRepository;
		
		@Override
		public Book save(Book book) {
			return bookRepository.save(book);
		}

		@Override
		public List<Book> findAllBooks() {
			List<Book> books = bookRepository.findAll();
			return books.stream().sorted(Comparator.comparing(Book::getId).reversed()).collect(Collectors.toList());
		}

		@Override
		public Optional<Book> findOne(Long id) {
			return bookRepository.findById(id);
		}

		@Override
		public Book findBookById(Long id) {
			return bookRepository.findBookById(id);
		}

		@Override
		public void removeOne(long parseLong) {
			bookRepository.deleteById(parseLong);
			
		}
		
		@Override
		public Long countBook(boolean active) {
			return bookRepository.count(active);
		}
		

		@Override
		public List<Book> findByCategoryId(Long id) {
			List<Book> bookList = bookRepository.findByCategoryId(id);
			List<Book> activeBookList = new ArrayList<>();
			for (Book book : bookList) {
				if (book.isActive()) {
					activeBookList.add(book);
				}
			}
			return activeBookList;
		}
		
//		@Override
//		public List<Book> findByCategory(String category) {
//			List<Book> bookList = bookRepository.findByCategory(category);
//			List<Book> activeBookList = new ArrayList<>();
//			for (Book book : bookList) {
//				if (book.isActive()) {
//					activeBookList.add(book);
//				}
//			}
//			return activeBookList;
//		}

		@Override
		public List<Book> findBookByName(String bookTitle) {
			// TODO Auto-generated method stub
			return bookRepository.findByTitle(bookTitle);
		}

	}

