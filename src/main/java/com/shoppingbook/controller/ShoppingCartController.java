package com.shoppingbook.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.service.BookService;
import com.shoppingbook.service.CartItemService;
import com.shoppingbook.service.ShoppingCartService;
import com.shoppingbook.service.UserService;

@Controller
@RequestMapping("account/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private BookService bookService;
	
	@GetMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		shoppingCartService.updateShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItems);
		model.addAttribute("shoppingCart", shoppingCart);
		return "user/shoppingCart";
	}
	@RequestMapping("/addItem")
	public String addItem(@ModelAttribute("book") Book book,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		book = bookService.findBookById(book.getId());
		
		if (Integer.parseInt(qty) > book.getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/bookDetail?id=" + book.getId();
		}
		cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));
		model.addAttribute("addBookSuccess", true);
		return "forward:/bookDetail?id=" + book.getId();
	}
	@PostMapping("updateCartItem")
	public String updateShoppingCart(@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty) {
		Optional<CartItem> cartItem = cartItemService.findById(cartItemId);
		cartItem.get().setQty(qty);
		cartItemService.updateCartItem(cartItem.get());
		return "redirect:/account/shoppingCart/cart";
	}
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id).get().getId());	
		return "forward:/account/shoppingCart/cart";
	}
}
