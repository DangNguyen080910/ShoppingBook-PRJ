package com.shoppingbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingbook.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	User findByUsername(String username);
	
	@Query("select u from User u where u.id =:id")
	User findUserById(@Param("id") Long id);
	
	@Query("select count(id) from User where enabled = :enabled")
	public Long count(@Param("enabled") boolean enabled);
	
	

}
