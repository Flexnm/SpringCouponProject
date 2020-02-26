package com.example.SpringBootCouponProject.facades.exceptions;

public class CouponExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponExistsException() {
		super("Coupon already exists");
	}

}
