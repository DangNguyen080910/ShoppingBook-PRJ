package com.shoppingbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.UserPayment;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long>{
	@Modifying
	@Query("select u from UserPayment u where u.userId =:user_id")
	List<UserPayment> findUserPaymentsByUserId(@Param("user_id") Long user_id);
}
