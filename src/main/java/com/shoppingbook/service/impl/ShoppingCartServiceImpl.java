package com.shoppingbook.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.repository.ShoppingCartRepository;
import com.shoppingbook.service.CartItemService;
import com.shoppingbook.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
	
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		
		for (CartItem cartItem : cartItems) {
			if (cartItem.getBook().getInStockNumber()>0) {
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubtotal());
			}
		}
		shoppingCart.setGrandTotal(cartTotal);
		shoppingCartRepository.save(shoppingCart);
		return shoppingCart;
	}
	@Override
	public ShoppingCart findByUser(User user) {
		return null;
	}
	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		for (CartItem cartItem : cartItems) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}
		shoppingCart.setGrandTotal(new BigDecimal(0));
		shoppingCartRepository.save(shoppingCart);
	}

}
