package com.example.SpringBootCouponProject.facades.exceptions;

public class CannotUpdateCompanyNameOrIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CannotUpdateCompanyNameOrIdException() {
		super("Cannot change Company name or id");
	}

}
