package com.shoppingbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.CommentRating;
import com.shoppingbook.service.CommentRatingService;

@Controller
public class CommentRatingController {

	@Autowired
	private CommentRatingService cmrtService;
	
	@GetMapping("/admin/cmrtList")
	public String categoryList(Model model) {
		List<CommentRating> cmrtList = cmrtService.findAllCmrt();
		model.addAttribute("cmrtList", cmrtList);
		return "admin/cmrtList";
	}
	
	@GetMapping("/user/cmrt/add")
	public String addCmrt(Model model) {
		model.addAttribute("crmt", new CommentRating());
		return "user/bookDetail";
	}	
	@PostMapping("/user/cmrt/add")
	public String add(@ModelAttribute("cmrt") CommentRating cmrt, Book book) {
		cmrtService.save(cmrt);		
		return "forward:/bookDetail?id=" + book.getId();	
	}
	
	@PostMapping("/admin/cmrt/remove")
	@ResponseBody
	public String removeCategory(@RequestParam("idcmrt") int id, Model model) {
		cmrtService.removeOne(id);
		return "ok";
	}
	
}
