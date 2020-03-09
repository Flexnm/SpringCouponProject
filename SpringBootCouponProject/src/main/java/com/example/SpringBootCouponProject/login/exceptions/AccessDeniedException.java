package com.example.SpringBootCouponProject.login.exceptions;

public class AccessDeniedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccessDeniedException() {
		super("Incorrect password or username!");
	}

}
