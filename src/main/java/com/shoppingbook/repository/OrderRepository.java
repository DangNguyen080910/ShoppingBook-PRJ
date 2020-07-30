package com.shoppingbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("select o from Order o where o.id =:id")
	Order findOrderById(@Param("id") Long id);
	
	@Query("select count(id) from Order where active = :active")
	public Long count(@Param("active") boolean active);
	
}
