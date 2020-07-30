package com.shoppingbook.service;

import com.shoppingbook.entity.ShippingAddress;
import com.shoppingbook.entity.UserShipping;

public interface ShippingAddressService {

	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);

}
