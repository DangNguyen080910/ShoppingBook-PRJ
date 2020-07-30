package com.shoppingbook.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.User;
import com.shoppingbook.entity.UserShipping;
import com.shoppingbook.service.CartItemService;
import com.shoppingbook.service.OrderService;
import com.shoppingbook.service.UserService;
import com.shoppingbook.utility.VNConstants;

@Controller
@RequestMapping("account")
public class OrderDetailController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartItemService cartItemService;
	
	@GetMapping("orderDetail")
	public String orderDetail(@RequestParam("id") Long orderId,
			Principal principal, Model model) {
		User user = userService.findByEmail(principal.getName());
		Order order = orderService.findById(orderId);
		
		if (order.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			List<CartItem> cartItems = cartItemService.findByOrder(order);
			model.addAttribute("cartItemList", cartItems);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			
			model.addAttribute("userPaymentList", user.getPaymentList());
			model.addAttribute("userShippingList", user.getShippingList());
			model.addAttribute("orderList", user.getOrderList());
			
			UserShipping userShipping = new UserShipping();
			model.addAttribute("userShipping", userShipping);
			
			List<String> stateList = VNConstants.listOfVNStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("activeOrders", true);
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("displayOrderDetail", true);
			
			return "user/myProfile";
		}
	}
}
