package com.shoppingbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	List<CartItem> findByOrder(Order order);

}
