package com.shoppingbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.UserShipping;

public interface UserShippingRepository extends JpaRepository<UserShipping, Long>{
	
	@Modifying
	@Query("select us from UserShipping us where us.userId =:user_id")
	List<UserShipping> findUserShippingByUserId(@Param("user_id") Long user_id);
}
