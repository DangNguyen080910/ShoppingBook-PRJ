package com.shoppingbook.service;

import com.shoppingbook.entity.Payment;
import com.shoppingbook.entity.UserPayment;

public interface PaymentService {

	Payment setByUserPayment(UserPayment userPayment, Payment payment);

}
