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

import com.shoppingbook.entity.User;
import com.shoppingbook.service.UserService;

@Controller	
public class AdminUserController {
	
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/admin/userList")
	public String userList(Model model) {
		List<User> userList = userService.findAllUser();
		model.addAttribute("userList", userList);
		return "admin/userList";
	}
	
	
	@GetMapping("admin/user/info")
	public String userinfo(@RequestParam("id") Long id, Model model) {
		userService.findOne(id).ifPresent(user -> model.addAttribute("user", user));
		return "admin/userinfo";
	}
	
	@GetMapping("admin/user/edit")
	public String editUser(@RequestParam("id") Long id, Model model) {
		userService.findOne(id).ifPresent(user -> model.addAttribute("user", user));
		return "admin/updateUser";
	}
	
	@PostMapping("admin/user/edit")
	public String updateUser(@ModelAttribute("user") User user) {
		userService.save(user);		
		return "redirect:/admin/user/info?id=" + user.getId();
	}
	
	@PostMapping("/admin/user/remove")
	@ResponseBody
	public String removeuser(@RequestParam("iduser") int id, Model model) {
		userService.removeOne(id);
		return "ok";
	}
	
	@PostMapping("/admin/user/ban")
	@ResponseBody
	public String banuser(@RequestParam("iduser") Long id, Model model) {
		userService.banOneUser(id);
		return "ok";
	}
	
	@PostMapping("/admin/user/unban")
	@ResponseBody
	public String unbanuser(@RequestParam("iduser") Long id, Model model) {
		userService.unbanOneUser(id);
		return "ok";
	}

}
