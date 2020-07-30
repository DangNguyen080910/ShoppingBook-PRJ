package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shoppingbook.entity.BillingAddress;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.Payment;
import com.shoppingbook.entity.ShippingAddress;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);
	Order findById(Long id);
	
	List<Order> findAllOrder();	
	Optional<Order> findOne(Long id);
	Order findOrderById(Long id);
	void removeOne(long parseLong);
	public Long countOrder(boolean active);
	void dlvOneOrder(Long id);
	void ndlvOneOrder(Long id);
	boolean createPdf(List<Order> orderList, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);	
	boolean createExcel(List<Order> orderList, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);
	boolean printBill(Order order, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);
}
