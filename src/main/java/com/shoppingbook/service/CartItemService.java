package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	CartItem updateCartItem(CartItem cartItem);

	CartItem addBookToCartItem(Book book, User user, int parseInt);

	Optional<CartItem> findById(Long cartItemId);

	void removeCartItem(Long id);

	void save(CartItem cartItem);

	List<CartItem> findByOrder(Order order);

}
