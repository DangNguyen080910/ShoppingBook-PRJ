package com.shoppingbook.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.shoppingbook.entity.Book;
import com.shoppingbook.service.BookService;

@Component
public class DuplicationValidator implements Validator{
	
	@Autowired
	private BookService bookService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// kiểm tra đối tượng truyền vào
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		
		if (book.getTitle() != null) {
			List<Book> books = bookService.findBookByName(book.getTitle());
			
			if (books != null && !books.isEmpty()) {
				if (book.getId() != null) {
					if (books.get(0).getId() != book.getId()) {
						errors.rejectValue("title", "duplicate.title.exist");
					}
				} else {
					errors.rejectValue("title", "duplicate.title.exist");
				}
			}
		}
		
	}
}
