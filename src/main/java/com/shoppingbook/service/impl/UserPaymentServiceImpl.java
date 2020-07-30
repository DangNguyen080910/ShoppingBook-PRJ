package com.shoppingbook.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.repository.UserPaymentRepository;
import com.shoppingbook.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{
	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	@Override
	public List<UserPayment> findUserPaymentsById(Long id) {
		return userPaymentRepository.findUserPaymentsByUserId(id);
	}
	@Override
	public Optional<UserPayment> findById(Long creditCardId) {
		return userPaymentRepository.findById(creditCardId);
	}
	@Override
	public void removeById(Long creditCardId) {
		userPaymentRepository.deleteById(creditCardId);
	}

}
