package com.shoppingbook.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.Category;
import com.shoppingbook.entity.User;
import com.shoppingbook.service.BookService;
import com.shoppingbook.service.CategoryService;
import com.shoppingbook.service.UserService;

@Controller
public class SearchController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("searchByCategory")
	public String searchByCategory(@RequestParam("category") String category,
			Model model, Principal principal) {
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		Category categoryNew = categoryService.findByCategory(category);

		String classActiveCategory = "active" + category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		model.addAttribute(classActiveCategory, true);
		List<Book> bookList = bookService.findByCategoryId(categoryNew.getId());
		if (bookList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "user/bookshelf";
		}
		model.addAttribute("bookList", bookList);
		return "user/bookshelf";
	}
}
