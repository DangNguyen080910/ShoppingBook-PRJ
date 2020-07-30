package com.shoppingbook.service;

import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;

public interface ShoppingCartService {

	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	ShoppingCart findByUser(User user);
	void clearShoppingCart(ShoppingCart shoppingCart);
}
