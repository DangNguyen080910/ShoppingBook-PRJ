package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import com.shoppingbook.entity.UserPayment;

public interface UserPaymentService {
	List<UserPayment> findUserPaymentsById(Long id);

	Optional<UserPayment> findById(Long creditCardId);

	void removeById(Long creditCardId);
}
