package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import com.shoppingbook.entity.UserShipping;

public interface UserShippingService {
	List<UserShipping> findUserShippingByUserId(Long user_id);

	Optional<UserShipping> findById(Long id);

	void removeById(Long id);
}
