package com.shoppingbook.service.impl;

import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Payment;
import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Override
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());
		
		return payment;
	}

}
