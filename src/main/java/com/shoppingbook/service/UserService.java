package com.shoppingbook.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.shoppingbook.entity.PasswordResetToken;
import com.shoppingbook.entity.User;
import com.shoppingbook.entity.UserBilling;
import com.shoppingbook.entity.UserPayment;
import com.shoppingbook.entity.UserShipping;

public interface UserService {

	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final String token , final User user);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createNewUser(User model, HttpServletRequest request);
	
	User updatePassword(String email, HttpServletRequest request);
	
	User save(User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long defaultId, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(Long id, User user);
		
	List<User> findAllUser();	
	Optional<User> findOne(Long id);
	User findUserById(Long id);
	void removeOne(long parseLong);
	void banOneUser(Long id);
	void unbanOneUser(Long id);
	public Long countUser(boolean enabled);

	
}
