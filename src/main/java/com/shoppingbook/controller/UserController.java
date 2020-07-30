package com.shoppingbook.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shoppingbook.entity.User;
import com.shoppingbook.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@RequestMapping("/loginUser")
	public String loginUSer(Model model) {
		model.addAttribute("activeLogin", true);
		model.addAttribute("user", new User());
		return "user/myAccount";
	}
	@RequestMapping("/createNewUser")
	public String createNewUSer(Model model) {
		model.addAttribute("activeNewUser", true);
		model.addAttribute("user", new User());
		return "user/myAccount";
	}
	@PostMapping("/createNewUser")
	public String createNewUser(Model model, @ModelAttribute("user") @Valid User user, 
			BindingResult result, HttpServletRequest request) {
		model.addAttribute("activeNewUser", true);
		if (result.hasErrors()) {
			return "user/myAccount";
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			model.addAttribute("emailExist", true);
			return "user/myAccount";
		}
		userService.createNewUser(user, request);
		model.addAttribute("emailSent", true);
		model.addAttribute("orderList", user.getOrderList());
		return "user/myAccount";
	}
	
	
	@RequestMapping("forgetPassword")
	public String forgetPassword(Model model) {
		model.addAttribute("activeForgetPw", true);
		model.addAttribute("user", new User());
		return "user/myAccount";
	}
	@PostMapping("forgetPassword")
	public String forgetPassword(Model model, @ModelAttribute("user") @Valid User user, BindingResult result,
			HttpServletRequest request, String email) {
		model.addAttribute("activeForgetPw", true);
		
		if (userService.findByEmail(email) == null) {
			model.addAttribute("emailNotExist", true);
			return "user/myAccount";
		}
		userService.updatePassword(email, request);
		model.addAttribute("forgetPasswordEmailSent", true);
		return "user/myAccount";
	}
	@PostMapping("/account/updateUserInfo")
	public String updateUserInfo(@ModelAttribute("user") User user,
			@ModelAttribute("newPassword") String newPassword,
			@ModelAttribute("currentPassword") String currentPassword,
			Model model, Principal principal) throws Exception {
		
		User currentUser = userService.findByEmail(principal.getName());
		if (currentUser == null) {
			throw new Exception("User not exist");
		}
		
		/*update password*/
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String dbPassword = currentUser.getPassword();
			if (passwordEncoder.matches(currentPassword, dbPassword)) {
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} else {
				model.addAttribute("incorrectPassword", true);
			}
		}
		currentUser.setUsername(user.getUsername());
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);
		model.addAttribute("activeEdit", true);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(currentUser.getEmail());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "user/myProfile";
	}
}
