package com.shoppingbook.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingbook.entity.PasswordResetToken;
import com.shoppingbook.entity.User;
import com.shoppingbook.entity.UserBilling;
import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.entity.UserShipping;
import com.shoppingbook.service.UserPaymentService;
import com.shoppingbook.service.UserService;
import com.shoppingbook.service.UserShippingService;
import com.shoppingbook.utility.MonthConstants;
import com.shoppingbook.utility.VNConstants;
import com.shoppingbook.utility.YearConstants;


@Controller
public class AccountController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserPaymentService userPaymentService;
	@Autowired
	private UserShippingService userShippingService;
	
	@RequestMapping("account")
	public String account(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("activeEdit", true);
		return "user/myProfile";
	}
	@GetMapping("updateUser")
	public String updateUser(Model model, Locale locale, @RequestParam("token") String token) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);
		if (passToken == null) {
			model.addAttribute("message", "Invalid Token");
			return "redirect:/badRequest";
		}
		User user = passToken.getUser();
		String email = user.getEmail();
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("activeEdit", true);
		model.addAttribute("user", user);
		return "user/myProfile";
	}
	@RequestMapping("account/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		Long user_id = user.getId();
		List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
		model.addAttribute("paymentList", userPayments);
		
		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
		model.addAttribute("shippingList", userShippings);
		
		model.addAttribute("orderList", user.getOrderList());
		
		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		List<String> stateList = VNConstants.listOfVNStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("activeEdit", true);
		
		return "user/myProfile";
	}
	@RequestMapping("/account/listOfCreditCards")
	public String listOfCreditCard(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		Long user_id = user.getId();
		List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
		model.addAttribute("paymentList", userPayments);
		model.addAttribute("orderList", user.getOrderList());
		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
		model.addAttribute("userShipping", userShippings);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("activeBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "user/myProfile";
	}
	@RequestMapping("/account/listOfShippingAddresses")
	public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		Long user_id = user.getId();
		List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
		model.addAttribute("paymentList", userPayments);
		model.addAttribute("orderList", user.getOrderList());
		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
		model.addAttribute("shippingList", userShippings);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("activeShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "user/myProfile";
	}
	@RequestMapping("account/addNewCreditCard")
	public String addNewCreditCard(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		
		Long user_id = user.getId();
		List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
		model.addAttribute("paymentList", userPayments);
		model.addAttribute("orderList", user.getOrderList());
		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
		model.addAttribute("userShipping", userShippings);
		
		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("activeBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();
		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);
		
		List<String> stateList = VNConstants.listOfVNStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		List<String> monthList = MonthConstants.listOfMonthCodes;
		Collections.sort(monthList);
		model.addAttribute("monthList", monthList);
		
		List<Integer> yearList = YearConstants.yearList;
		Collections.sort(yearList);
		model.addAttribute("yearList", yearList);
		
		return "user/myProfile";
	}
	
	@PostMapping("/account/addNewCreditCard")
	public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			Principal principal, Model model){
		User user = userService.findByEmail(principal.getName());
		
		model.addAttribute("user", user);
		userService.updateUserBilling(userBilling, userPayment, user);
		
		return "redirect:/account/listOfCreditCards";
	}
	
	@RequestMapping("account/updateCreditCard")
	public String updateCreditCard(@ModelAttribute("id") Long creditCardId, 
			Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserPayment> userPayment = userPaymentService.findById(creditCardId);
		Long user_id = user.getId();
		if (user_id != userPayment.get().getUserId()) {
			return "badRequestPage";
		} else {
			
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.get().getUserBilling();
			model.addAttribute("userPayment", userPayment.get());
			model.addAttribute("userBilling", userBilling);
			
			List<String> stateList = VNConstants.listOfVNStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			List<String> monthList = MonthConstants.listOfMonthCodes;
			model.addAttribute("monthList", monthList);
			
			List<Integer> yearList = YearConstants.yearList;
			Collections.sort(yearList);
			model.addAttribute("yearList", yearList);
			
			List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
			model.addAttribute("paymentList", userPayments);
			model.addAttribute("orderList", user.getOrderList());
			List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
			model.addAttribute("userShipping", userShippings);
			
			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("activeBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			
			return "user/myProfile";
		}
		
	}
	
	@RequestMapping("/account/removeCreditCard")
	public String removeCreditCard(@ModelAttribute("id") Long creditCardId, 
			Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserPayment> userPayment = userPaymentService.findById(creditCardId);
		Long userId = user.getId();
		if (userId != userPayment.get().getUserId()) {
			return "badRequestPage";
		} else {
			userPaymentService.removeById(creditCardId);
			
			return "redirect:/account/listOfCreditCards";
		}
	}
	
	@RequestMapping("account/addNewShippingAddress")
	public String addNewShippingAddress(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user", user);
		
		Long user_id = user.getId();
		List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
		model.addAttribute("paymentList", userPayments);
		
		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
		model.addAttribute("userShipping", userShippings);
		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("activeShipping", true);
		model.addAttribute("listOfCreditCards", true);
		
		UserShipping userShipping = new UserShipping();	
		model.addAttribute("userShipping", userShipping);
		
		List<String> stateList = VNConstants.listOfVNStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		
		return "user/myProfile";
	}
	
	@PostMapping("account/addNewShippingAddress")
	public String addNewShippingAddress(@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		userService.updateUserShipping(userShipping, user);
		
		return "redirect:/account/listOfShippingAddresses";
	}
	
	@GetMapping("account/updateUserShipping")
	public String updateUserShipping(@ModelAttribute("id") Long id, Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserShipping> userShipping = userShippingService.findById(id);
		if (user.getId() != userShipping.get().getUserId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("userShipping", userShipping.get());
			List<String> stateList = VNConstants.listOfVNStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("activeShipping", true);
			model.addAttribute("listOfCreditCards", true);
			
			Long user_id = user.getId();
			List<UserPayment> userPayments = userPaymentService.findUserPaymentsById(user_id);
			model.addAttribute("paymentList", userPayments);
			model.addAttribute("orderList", user.getOrderList());
			List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(user_id);
			model.addAttribute("userShippings", userShippings);
			
			return "user/myProfile";
		}
	}
	
	@PostMapping("account/setDefaultShippingAddress")
	public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long id,
			Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		userService.setUserDefaultShipping(id, user);
		
		return "redirect:/account/listOfShippingAddresses";
	}
	
	@GetMapping("account/removeUserShipping")
	public String removeUserShipping(@ModelAttribute("id") Long id, Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserShipping> userShipping = userShippingService.findById(id);
		if (user.getId() != userShipping.get().getUserId()) {
			return "badRequestPage";
		} else {
			userShippingService.removeById(id);
			return "redirect:/account/listOfShippingAddresses";
		}
	}
	
	@PostMapping("account/setDefaultPayment")
	public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultId, Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		userService.setUserDefaultPayment(defaultId, user);
		return "redirect:/account/listOfCreditCards";
	}
	
	
}
