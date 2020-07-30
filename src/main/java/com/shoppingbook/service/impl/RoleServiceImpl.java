package com.shoppingbook.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Role;
import com.shoppingbook.repository.RoleRepository;
import com.shoppingbook.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(String id) {
		return roleRepository.findRoleById(id);
	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public boolean insertRole(Role model) {
		model.setId(UUID.randomUUID().toString());
		roleRepository.save(model);
		return true;
	}

	@Override
	public boolean deleteRole(String id) {
		roleRepository.deleteById(id);
		return true;
	}

}
