package com.shoppingbook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingbook.entity.Role;
import com.shoppingbook.service.RoleService;

@Controller
public class AdminRoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("admin/role")
	public String role(Model model) {
		model.addAttribute("roles", roleService.findAll());
		return "admin/role";
	}
	@RequestMapping("admin/role/add")
	public String addRole(Model model) {
		model.addAttribute("role", new Role());
		return "admin/role-add";
	}
	@PostMapping("admin/role/add")
	public String addRole(Model model, @ModelAttribute("role") @Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return "admin/role-add";
		}
		roleService.insertRole(role);
		return "redirect:/admin/role";
	}
}
