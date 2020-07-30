package com.shoppingbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingbook.entity.Category;
import com.shoppingbook.service.CategoryService;

@Controller
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin/categoryList")
	public String categoryList(Model model) {
		List<Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		return "admin/categoryList";
	}
	 
	
	@GetMapping("admin/category/add")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "admin/addCategory";
	}
		
	@PostMapping("admin/category/add")
	public String addCategory(@ModelAttribute("id") Category category) {
		categoryService.save(category);
		return "redirect:/admin/categoryList";
	}
	
	@GetMapping("admin/category/edit")
	public String editCategory(@RequestParam("id") Long id, Model model) {
		categoryService.findOne(id).ifPresent(category -> model.addAttribute("category", category));
		return "admin/editCategory";
	}
	@PostMapping("admin/category/edit/{id}")
	public String updateCategory(@PathVariable("id") long id,@ModelAttribute("id") Category category, 
			BindingResult result, Model model) {
		categoryService.save(category);
		return "redirect:/admin/categoryList";
	}
	
	@PostMapping("/admin/category/remove")
	@ResponseBody
	public String removeCategory(@RequestParam("idcategory") int id, Model model) {
		categoryService.removeOne(id);
		return "ok";
	}

}
