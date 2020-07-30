package com.shoppingbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingbook.entity.BookToCartItem;

public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long>{

}
