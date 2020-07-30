package com.shoppingbook.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingbook.entity.PasswordResetToken;
import com.shoppingbook.entity.Role;
import com.shoppingbook.entity.ShoppingCart;
import com.shoppingbook.entity.User;
import com.shoppingbook.entity.UserBilling;
import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.entity.UserShipping;
import com.shoppingbook.repository.PasswordResetTokenRepository;
import com.shoppingbook.repository.RoleRepository;
import com.shoppingbook.repository.UserPaymentRepository;
import com.shoppingbook.repository.UserRepository;
import com.shoppingbook.repository.UserShippingRepository;
import com.shoppingbook.service.UserService;
import com.shoppingbook.utility.MailConstructor;
import com.shoppingbook.utility.SecurityUtility;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailConstructor mailConstructor;
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	@Autowired
	private UserShippingRepository userShippingRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	@Override
	public void createPasswordResetTokenForUser(final String token ,final User user) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}
	
	@Override
	public Optional<User> findOne(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findUserById(id);
	}
	
	@Override
	public void removeOne(long parseLong) {
		userRepository.deleteById(parseLong);
		
	}
	
	@Override
	public Long countUser(boolean enabled) {
		return userRepository.count(enabled);
	}
	
	
	@Override
	@Transactional
	public User createNewUser(User model, HttpServletRequest request) {
		User newUser = new User();
		newUser.setUsername(model.getUsername());
		newUser.setEmail(model.getEmail());
		
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		newUser.setPassword(encryptedPassword);
		
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findByName("ROLE_USER");
		roles.add(role);
		newUser.setRoles(roles);
		userRepository.save(newUser);
		
		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(token, newUser);
		
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUserId(newUser.getId());
		newUser.setShoppingCart(shoppingCart);
		
		newUser.setShippingList(new ArrayList<UserShipping>());
		newUser.setPaymentList(new ArrayList<UserPayment>());
		
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, newUser, password);
		mailSender.send(email);
		
		return newUser;
	}
	@Override
	public User updatePassword(String email, HttpServletRequest request) {
		User user = userRepository.findByEmail(email);
		
		String password = SecurityUtility.randomPassword();
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(token, user);
		
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SimpleMailMessage mail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		mailSender.send(mail);
		
		return user;
	}
	
	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		Long userId = user.getId();
		userPayment.setUserId(userId);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getPaymentList().add(userPayment);
		save(user);
	}
	@Override
	public User save(User user) {
		 return userRepository.save(user);
	}
	@Override
	public void setUserDefaultPayment(Long defaultId, User user) {
		List<UserPayment> paymentList = userPaymentRepository.findAll();
		for (UserPayment userPayment : paymentList) {
			if (userPayment.getId() == defaultId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
		
	}
	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		userShipping.setUserId(user.getId());
		userShipping.setUserShippingDefault(true);
		user.getShippingList().add(userShipping);
		save(user);
	}
	@Override
	public void setUserDefaultShipping(Long id, User user) {
		List<UserShipping> shippingList = userShippingRepository.findAll();
		
		for (UserShipping userShipping : shippingList) {
			if (userShipping.getId() == id) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
	}
	@Override
	public void banOneUser(Long id) {
		User user = userRepository.getOne(id);
		user.setEnabled(false);
		save(user);
	}
	@Override
	public void unbanOneUser(Long id) {
		User user = userRepository.getOne(id);
		user.setEnabled(true);
		save(user);
	}

}











