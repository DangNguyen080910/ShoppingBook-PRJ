package com.shoppingbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/loginAdmin")
	public String login1() {
		return "admin/loginAdmin";
	}
	@RequestMapping("/admin/admin")
	public String admin() {
		return "admin/admin";
	}
}
