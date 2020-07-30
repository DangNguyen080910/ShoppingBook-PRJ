package com.shoppingbook.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoppingbook.entity.BillingAddress;
import com.shoppingbook.entity.CartItem;
import com.shoppingbook.entity.Order;
import com.shoppingbook.entity.Payment;
import com.shoppingbook.entity.ShippingAddress;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.entity.UserBilling;
import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.entity.UserShipping;
import com.shoppingbook.service.BillingAddressService;
import com.shoppingbook.service.CartItemService;
import com.shoppingbook.service.OrderService;
import com.shoppingbook.service.PaymentService;
import com.shoppingbook.service.ShippingAddressService;
import com.shoppingbook.service.ShoppingCartService;
import com.shoppingbook.service.UserPaymentService;
import com.shoppingbook.service.UserService;
import com.shoppingbook.service.UserShippingService;
import com.shoppingbook.utility.MailConstructor;
import com.shoppingbook.utility.MonthConstants;
import com.shoppingbook.utility.VNConstants;
import com.shoppingbook.utility.YearConstants;

@Controller
public class CheckoutController {

	private ShippingAddress shippingAddress = new ShippingAddress();
	private BillingAddress billingAddress = new BillingAddress();
	private Payment payment = new Payment();

	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BillingAddressService billingAddressService;
	@Autowired
	private UserShippingService userShippingService;
	@Autowired
	private UserPaymentService userPaymentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailConstructor mailConstructor;
	@Autowired
	private ShoppingCartService shoppingCartService;

	@GetMapping("account/checkout")
	public String checkout(@RequestParam("id") Long cartId,
			@RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField, Model model,
			Principal principal) {
		User user = userService.findByEmail(principal.getName());

		if (cartId != user.getShoppingCart().getId()) {
			return "badRequestPage";
		}
		List<CartItem> cartItems = cartItemService.findByShoppingCart(user.getShoppingCart());
		if (cartItems.size() == 0) {
			model.addAttribute("empty", true);
			return "forward:/account/shoppingCart/cart";
		}
		for (CartItem cartItem : cartItems) {
			if (cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
				model.addAttribute("notEnoughStock", true);
				return "forward:/account/shoppingCart/cart";
			}
		}
		Long userId = user.getId();

		List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(userId);
		List<UserPayment> userPayments = user.getPaymentList();

		if (userShippings.size() == 0) {
			model.addAttribute("emptyShippingList", true);
		} else {
			model.addAttribute("emptyShippingList", false);
		}
		if (userPayments.size() == 0) {
			model.addAttribute("emptyPaymentList", true);
		} else {
			model.addAttribute("emptyPaymentList", false);
		}

		for (UserShipping userShipping : userShippings) {
			if (userShipping.isUserShippingDefault()) {
				shippingAddressService.setByUserShipping(userShipping, shippingAddress);
			}
		}
		for (UserPayment userPayment : userPayments) {
			if (userPayment.isDefaultPayment()) {
				paymentService.setByUserPayment(userPayment, payment);
				billingAddressService.setByUserBilling(userPayment.getUserBilling(), billingAddress);
			}
		}
		model.addAttribute("shippingAddress", shippingAddress);
		model.addAttribute("payment", payment);
		model.addAttribute("userPaymentList", userPayments);
		model.addAttribute("userShippingList", userShippings);
		model.addAttribute("billingAddress", billingAddress);
		model.addAttribute("cartItemList", cartItems);
		model.addAttribute("shoppingCart", user.getShoppingCart());

		List<String> stateList = VNConstants.listOfVNStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		
		List<String> monthList = MonthConstants.listOfMonthCodes;
		Collections.sort(monthList);
		model.addAttribute("monthList", monthList);
		
		List<Integer> yearList = YearConstants.yearList;
		Collections.sort(yearList);
		model.addAttribute("yearList", yearList);

		model.addAttribute("classActiveShipping", true);
		if (missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}

		return "user/checkout";
	}

	@RequestMapping("/account/setShippingAddress")
	public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId, Principal principal,
			Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserShipping> userShipping = userShippingService.findById(userShippingId);
		if (userShipping.get().getUserId() != user.getId()) {
			return "badRequestPage";
		} else {
			Long userId = user.getId();

			List<UserShipping> userShippings = userShippingService.findUserShippingByUserId(userId);
			List<UserPayment> userPayments = user.getPaymentList();
			shippingAddressService.setByUserShipping(userShipping.get(), shippingAddress);
			List<CartItem> cartItems = cartItemService.findByShoppingCart(user.getShoppingCart());

			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("userPaymentList", userPayments);
			model.addAttribute("userShippingList", userShippings);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItems);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			List<String> stateList = VNConstants.listOfVNStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			model.addAttribute("classActiveShipping", true);

			if (userPayments.size() == 0) {
				model.addAttribute("emptyPaymentList", true);
			} else {
				model.addAttribute("emptyPaymentList", false);
			}

			model.addAttribute("emptyShippingList", false);

			return "user/checkout";
		}

	}

	@RequestMapping("/account/setPaymentMethod")
	public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal,
			Model model) {
		User user = userService.findByEmail(principal.getName());
		Optional<UserPayment> userPayment = userPaymentService.findById(userPaymentId);
		UserBilling userBilling = userPayment.get().getUserBilling();

		if (userPayment.get().getUserId() != user.getId()) {
			return "badRequestPage";
		} else {
			paymentService.setByUserPayment(userPayment.get(), payment);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			billingAddressService.setByUserBilling(userBilling, billingAddress);

			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());

			List<String> stateList = VNConstants.listOfVNStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);

			List<UserShipping> userShippingList = user.getShippingList();
			List<UserPayment> userPaymentList = user.getPaymentList();

			model.addAttribute("userShippingList", userShippingList);
			model.addAttribute("userPaymentList", userPaymentList);

			model.addAttribute("shippingAddress", shippingAddress);

			model.addAttribute("classActivePayment", true);

			if (userShippingList.size() == 0) {
				model.addAttribute("emptyShippingList", true);
			} else {
				model.addAttribute("emptyShippingList", false);
			}

			model.addAttribute("emptyPaymentList", false);

			return "user/checkout";
		}
	}

	@PostMapping("/account/checkout")
	public String checkoutPost(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
			@ModelAttribute("billingAddress") BillingAddress billingAddress,
			@ModelAttribute("payment") Payment payment,
			@ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
			@ModelAttribute("shippingMethod") String shippingMethod, 
			Principal principal, Model model) {
		
		User user = userService.findByEmail(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItems = cartItemService.findByShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItems);
		
		if (billingSameAsShipping.equals("true")) {
			billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
			billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
			billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
			billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
			billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
			billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
			billingAddress.setBillingAddressZipcode(shippingAddress.getShippingAddressZipcode());
		}
		if (shippingAddress.getShippingAddressStreet1().isEmpty()
				|| shippingAddress.getShippingAddressCity().isEmpty()
				|| shippingAddress.getShippingAddressState().isEmpty()
				|| shippingAddress.getShippingAddressName().isEmpty()
				|| shippingAddress.getShippingAddressZipcode().isEmpty() 
				|| payment.getCardNumber().isEmpty()
				|| payment.getCvc() == 0 
				|| billingAddress.getBillingAddressStreet1().isEmpty()
				|| billingAddress.getBillingAddressCity().isEmpty() 
				|| billingAddress.getBillingAddressName().isEmpty()
				|| billingAddress.getBillingAddressZipcode().isEmpty()) {
			return "redirect:/account/checkout?id=" + shoppingCart.getId() + "&missingRequiredField=true";
		}
		
		Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		
		if (shippingMethod.equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		} else {
			estimatedDeliveryDate = today.plusDays(3);
		}
		model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);
		
		return "user/orderSubmittedPage";
	}
}
