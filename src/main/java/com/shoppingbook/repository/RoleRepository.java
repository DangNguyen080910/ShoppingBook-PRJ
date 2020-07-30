package com.shoppingbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.shoppingbook.entity.Role;


public interface RoleRepository extends JpaRepository<Role, String>{

	Role findByName(String name);
	
	@Modifying
	@Query("select r from Role r where r.id =:id")
	Role findRoleById(@PathVariable("id") String id);
	
	

}
