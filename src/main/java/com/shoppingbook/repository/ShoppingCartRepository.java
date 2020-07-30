package com.shoppingbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbook.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
	
}
