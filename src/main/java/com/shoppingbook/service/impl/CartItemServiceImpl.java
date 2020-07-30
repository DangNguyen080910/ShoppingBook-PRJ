package com.shoppingbook.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Book;
import com.shoppingbook.entity.BookToCartItem;
import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.repository.BookToCartItemRepository;
import com.shoppingbook.repository.CartItemRepository;
import com.shoppingbook.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;
	
	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}
	@SuppressWarnings("deprecation")
	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
		bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		cartItemRepository.save(cartItem);
		return cartItem;
	}
	@Override
	public CartItem addBookToCartItem(Book book, User user, int parseInt) {
		List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItems) {
			if (book.getId() == cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty() + parseInt);
				cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(parseInt)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		cartItem.setQty(parseInt);
		cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(parseInt)));
		cartItemRepository.save(cartItem);
		
		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		bookToCartItemRepository.save(bookToCartItem);
		return cartItem;
	}
	@Override
	public Optional<CartItem> findById(Long cartItemId) {
		return cartItemRepository.findById(cartItemId);
	}
	@Override
	public void removeCartItem(Long id) {
		cartItemRepository.deleteById(id);
		
	}
	@Override
	public void save(CartItem cartItem) {
		cartItemRepository.save(cartItem);
		
	}
	@Override
	public List<CartItem> findByOrder(Order order) {
		return cartItemRepository.findByOrder(order);
	}

}
