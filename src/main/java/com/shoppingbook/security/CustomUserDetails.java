package com.shoppingbook.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.entity.UserShipping;

public class CustomUserDetails extends User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super(email, password, authorities);
		// TODO Auto-generated constructor stub
	}
	private List<UserShipping> shippingList;
	private List<UserPayment> paymentList;

	public List<UserShipping> getShippingList() {
		return shippingList;
	}
	public void setShippingList(List<UserShipping> shippingList) {
		this.shippingList = shippingList;
	}
	public List<UserPayment> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<UserPayment> paymentList) {
		this.paymentList = paymentList;
	}
	
}
