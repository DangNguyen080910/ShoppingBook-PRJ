package com.shoppingbook.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.Category;
import com.shoppingbook.service.BookService;
import com.shoppingbook.service.CategoryService;
import com.shoppingbook.validation.DuplicationValidator;

@Controller
public class AdminBookController {
	
	@Autowired
	private BookService bookService;	
	@Autowired
	private CategoryService categoryService;
	//thêm validator
	@Autowired
	private DuplicationValidator duplicationValidator;
	
	@GetMapping("/admin/bookList")
	public String bookList(Model model) {
		List<Book> bookList = bookService.findAllBooks();
		model.addAttribute("bookList", bookList);
		return "admin/bookList";
	}
	
	@GetMapping("admin/book/add")
	public String addBook(Model model) {
		model.addAttribute("book", new Book());
		
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		
		return "admin/addBook";
	}
	
	@PostMapping("admin/book/add")
	public String addBook(Model model, @ModelAttribute("book") @Validated Book book, BindingResult errors) {
		//validate title
		duplicationValidator.validate(book, errors);
		if (errors.hasErrors()) {
			List<Category> categoryList = categoryService.findAllCategories();
			model.addAttribute("categoryList", categoryList);
			System.out.println(errors);
			return "admin/addBook";
		}
		bookService.save(book);
		
		MultipartFile bookImage = book.getBookImage();
		try {
			byte[] bytes = bookImage.getBytes();
			String name = book.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/images/book/"+name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/admin/bookList";
	}
	
	@GetMapping("admin/book/info")
	public String bookInfo(@RequestParam("id") Long id, Model model) {
		bookService.findOne(id).ifPresent(book -> model.addAttribute("book", book));
		return "admin/bookinfo";
	}
	@GetMapping("admin/book/edit")
	public String editBook(@RequestParam("id") Long id, Model model) {
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		bookService.findOne(id).ifPresent(book -> model.addAttribute("book", book));
		return "admin/updateBook";
	}
	@PostMapping("admin/book/edit")
	public String updateBook(@ModelAttribute("book") @Validated Book book, BindingResult errors, Model model) {
		//validate title
		duplicationValidator.validate(book, errors);
		if (errors.hasErrors()) {
			List<Category> categoryList = categoryService.findAllCategories();
			model.addAttribute("categoryList", categoryList);
			return "admin/updateBook";
		}
		bookService.save(book);
		MultipartFile bookImage = book.getBookImage();
		if (!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = book.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/images/book/"+name));
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/images/book/"+name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/admin/book/info?id=" + book.getId();
	}
	
	@PostMapping("/admin/book/remove")
	@ResponseBody
	public String removeBook(@RequestParam("idbook") int id, Model model) {
		bookService.removeOne(id);
		return "ok";
	}
	
}
