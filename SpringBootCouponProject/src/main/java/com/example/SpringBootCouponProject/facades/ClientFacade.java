package com.example.SpringBootCouponProject.facades;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.SpringBootCouponProject.database.CompanyRepository;
import com.example.SpringBootCouponProject.database.CouponRepository;
import com.example.SpringBootCouponProject.database.CustomerRepository;

public abstract class ClientFacade {
	
	@Autowired
	protected CouponRepository coupRepo;
	@Autowired
	protected CompanyRepository compRepo;
	@Autowired
	protected CustomerRepository custRepo;
	
	public ClientFacade() {
		
	}
	
	
	public abstract boolean login(String email, String password);

}
