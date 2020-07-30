package com.shoppingbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("hours")
	public String direction() {
		return "user/directions";
	}

	@GetMapping("faq")
	public String faq() {
		return "user/faq";
	}
	@GetMapping("badRequest")
	public String badRequest() {
		return "user/badRequestPage";
	}
	
}
