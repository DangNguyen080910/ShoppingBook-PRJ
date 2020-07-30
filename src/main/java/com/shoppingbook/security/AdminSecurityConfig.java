package com.shoppingbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/admin/**").authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.and().formLogin()
		.loginProcessingUrl("/admin/security_login")
		.loginPage("/loginAdmin")
		.defaultSuccessUrl("/admin/adminPortal")
		.failureUrl("/loginAdmin?error")
		.usernameParameter("username")
		.passwordParameter("password")
		.and().exceptionHandling().accessDeniedPage("/403")
		.and().logout()
		.logoutUrl("/admin/security_logout")
		.logoutSuccessUrl("/loginAdmin?logout");
		
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/images/**", "/js/**");
	}

}
