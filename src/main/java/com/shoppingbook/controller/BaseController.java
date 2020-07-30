package com.shoppingbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {

	@RequestMapping("/index")
	public String user() {
		return "user/index";
	}

	@RequestMapping("/403")
	public String accessDenied403() {
		return "403";
	}

}
