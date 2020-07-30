package com.shoppingbook.service;

import com.shoppingbook.entity.BillingAddress;
import com.shoppingbook.entity.UserBilling;

public interface BillingAddressService {

	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);

}
