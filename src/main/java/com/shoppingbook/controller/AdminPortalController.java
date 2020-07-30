package com.shoppingbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shoppingbook.service.BookService;
import com.shoppingbook.service.OrderService;
import com.shoppingbook.service.UserService;

@Controller
public class AdminPortalController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@GetMapping("/admin/adminPortal")
	public String homeList(Model model) {
		
		long countUser = userService.countUser(true);
		model.addAttribute("countUser", countUser);
		
		long countBook = bookService.countBook(true);
		model.addAttribute("countBook", countBook);
		
		long countOrder = orderService.countOrder(true);
		model.addAttribute("countOrder", countOrder);
		
		long countNewOrder = orderService.countOrder(false);
		model.addAttribute("countNewOrder", countNewOrder);
		
		return "admin/adminPortal";
	}
	
}
