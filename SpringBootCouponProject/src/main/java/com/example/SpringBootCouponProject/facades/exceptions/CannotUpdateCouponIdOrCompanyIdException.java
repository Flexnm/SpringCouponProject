package com.example.SpringBootCouponProject.facades.exceptions;

public class CannotUpdateCouponIdOrCompanyIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CannotUpdateCouponIdOrCompanyIdException() {
		super("Can't update coupon id or company id of a coupon");
	}

}
