package com.shoppingbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserShipping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_shipping_name")
	private String userShippingName;
	@Column(name = "user_shipping_street1")
	private String userShippingStreet1;
	@Column(name = "user_shipping_street2")
	private String userShippingStreet2;
	@Column(name = "user_shipping_city")
	private String userShippingCity;
	@Column(name = "user_shipping_state")
	private String userShippingState;
	@Column(name = "user_shipping_country")
	private String userShippingCountry;
	@Column(name = "user_shipping_zipcode")
	private String userShippingZipcode;
	@Column(name = "user_shipping_default")
	private boolean userShippingDefault;
	@Column(name = "user_id")
	private Long userId;
	@ManyToOne
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserShippingName() {
		return userShippingName;
	}

	public void setUserShippingName(String userShippingName) {
		this.userShippingName = userShippingName;
	}

	public String getUserShippingStreet1() {
		return userShippingStreet1;
	}

	public void setUserShippingStreet1(String userShippingStreet1) {
		this.userShippingStreet1 = userShippingStreet1;
	}

	public String getUserShippingStreet2() {
		return userShippingStreet2;
	}

	public void setUserShippingStreet2(String userShippingStreet2) {
		this.userShippingStreet2 = userShippingStreet2;
	}

	public String getUserShippingCity() {
		return userShippingCity;
	}

	public void setUserShippingCity(String userShippingCity) {
		this.userShippingCity = userShippingCity;
	}

	public String getUserShippingState() {
		return userShippingState;
	}

	public void setUserShippingState(String userShippingState) {
		this.userShippingState = userShippingState;
	}

	public String getUserShippingCountry() {
		return userShippingCountry;
	}

	public void setUserShippingCountry(String userShippingCountry) {
		this.userShippingCountry = userShippingCountry;
	}

	public String getUserShippingZipcode() {
		return userShippingZipcode;
	}

	public void setUserShippingZipcode(String userShippingZipcode) {
		this.userShippingZipcode = userShippingZipcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isUserShippingDefault() {
		return userShippingDefault;
	}

	public void setUserShippingDefault(boolean userShippingDefault) {
		this.userShippingDefault = userShippingDefault;
	}
	
}
