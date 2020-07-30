package com.shoppingbook.service;

import java.util.List;

import com.shoppingbook.entity.Role;

public interface RoleService {
	List<Role> findAll();
	Role findById(String id);
	Role findByName(String name);
	boolean insertRole(Role model);
	boolean deleteRole(String id);
}
