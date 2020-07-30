package com.shoppingbook.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppingbook.entity.Role;
import com.shoppingbook.entity.User;
import com.shoppingbook.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Email không tồn tại");
		}
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<Role> roles = user.getRoles();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		CustomUserDetails userDetails = new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
		
		userDetails.setPaymentList(user.getPaymentList());
		userDetails.setShippingList(user.getShippingList());
		
		return userDetails;
	}

}
