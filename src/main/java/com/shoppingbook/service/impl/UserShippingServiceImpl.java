package com.shoppingbook.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.UserShipping;
import com.shoppingbook.repository.UserShippingRepository;
import com.shoppingbook.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService{
	
	@Autowired
	private UserShippingRepository userShippingRepository;
	@Override
	public List<UserShipping> findUserShippingByUserId(Long user_id) {
		return userShippingRepository.findUserShippingByUserId(user_id);
	}
	@Override
	public Optional<UserShipping> findById(Long id) {
		return userShippingRepository.findById(id);
	}
	@Override
	public void removeById(Long id) {
		userShippingRepository.deleteById(id);
	}

}
