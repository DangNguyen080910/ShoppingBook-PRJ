package com.shoppingbook.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.Category;
import com.shoppingbook.entity.CommentRating;
import com.shoppingbook.entity.User;
import com.shoppingbook.service.BookService;
import com.shoppingbook.service.CategoryService;
import com.shoppingbook.service.CommentRatingService;
import com.shoppingbook.service.UserService;

@Controller
public class BookshelfController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentRatingService cmrtService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("bookshelf")
	public String bookshelf(Model model) {
		List<Book> books = bookService.findAllBooks();
		model.addAttribute("bookList", books);
		
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		
		return "user/bookshelf";
	}
	@RequestMapping("bookDetail")
	public String bookDetail(Model model, @PathParam("id") Long id, Principal principal) {
				
		if (principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
			User userFindID = userService.findByEmail(principal.getName());
			model.addAttribute("userFindID", userFindID);
		}
		bookService.findOne(id).ifPresent(b ->model.addAttribute("book", b));
		List<Integer> qty = Arrays.asList(1,2,3,4,5,6,7,8,9);
		model.addAttribute("qtyList", qty);
		model.addAttribute("qty", 1);
		
		List<CommentRating> cmrtList = cmrtService.findByBookId(id);
		model.addAttribute("cmrtList", cmrtList);	
		
		LocalDate now = LocalDate.now();
		model.addAttribute("dateNow", now);
				
		return "user/bookDetail";
	}
	
	
		
}






















