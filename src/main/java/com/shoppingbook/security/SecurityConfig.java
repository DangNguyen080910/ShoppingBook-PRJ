package com.shoppingbook.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/account**").hasRole("USER").anyRequest().permitAll().and().formLogin()
				.loginProcessingUrl("/security_login").loginPage("/loginUser").defaultSuccessUrl("/account/myProfile")
				.failureUrl("/loginUser?error").usernameParameter("email").passwordParameter("password").and()
				.exceptionHandling().accessDeniedPage("/403").and().logout().logoutUrl("/security_logout")
				.logoutSuccessUrl("/loginUser?logout");
	}
}
