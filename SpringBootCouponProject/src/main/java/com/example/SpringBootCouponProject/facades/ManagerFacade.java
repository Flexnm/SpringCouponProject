package com.example.SpringBootCouponProject.facades;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.Company;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCompanyNameOrIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;

@Service // Component
public class ManagerFacade extends ClientFacade {
/**
 * no need to auto wire the repositories
 * because Client Facade has them all
 * 
 * */
	
	// ======================= login ==========================\\
	
	public boolean login(String email, String password) {
		if(email.equals("admin@admin.com") && password.equals("admin"))
			return true;
		else
			return false;
		
	}
	
	// ===================== Adding methods ==================== \\
	/**
	 * 1. Can't add a company with the same email or name.
	 * 
	 * 2. Can't add a customer with the same email
	 * 
	 * */
	
	public void addCompany(Company company) throws CompanyExistsException {
		if(compRepo.existsByEmail(company.getEmail()) 
				&& compRepo.existsByName(company.getName()))
			throw new CompanyExistsException();
		 else 
			compRepo.save(company);
	}
	
	public void addCustomer(Customer customer) throws CustomerExistsException {
		if(custRepo.existsByEmail(customer.getEmail()))
			throw new CustomerExistsException();
		else
			custRepo.save(customer);
	}
	
	// ==================== Updating methods ===================== \\
	/**
	 * 1. Can't update company name or id
	 * 
	 * 2. Can't update customer id
	 * 
	 * */
	
	public void updateCompany(Company company) throws 
	CannotUpdateCompanyNameOrIdException, CompanyNotFoundException {
		
		Company comp = compRepo.findById(company.getCompanyId())
				.orElseThrow(CompanyNotFoundException::new);
		
		if(comp.getName().equals(company.getName()) 
				&& comp.getCompanyId() == company.getCompanyId())
		compRepo.save(company);
		else
			throw new CannotUpdateCompanyNameOrIdException();
	}
	
	public void updateCustomer(Customer customer) throws CustomerNotFoundException, CustomerExistsException {
		
		Customer cust = custRepo.findById(customer.getCustomerId())
				.orElseThrow(CustomerNotFoundException::new);
		
		if(cust.getCustomerId() == customer.getCustomerId())
			custRepo.save(customer);
		else
			throw new CustomerExistsException();
			
	}
	
	// ==================== deleting methods ==================== \\
	/**
	 * 1. delete company with all its coupon and customer purchases
	 * 
	 * 2. delete customer with all his coupons
	 * 
	 * */
	
	public void deleteCompany(long companyId) throws CompanyNotFoundException {
		Company comp = compRepo.findById(companyId)
				.orElseThrow(CompanyNotFoundException::new);
		
		if(compRepo.existsById(companyId)) {
		for (Customer cust : custRepo.findAll()) {
			for (Coupon coup : cust.getCoupons()) {
				if(coup.getCompany().equals(comp))
						coupRepo.delete(coup);
			}
		}
		comp.getCoupons().removeAll(comp.getCoupons());// delete all coupons
		compRepo.save(comp); // do I have to update?
		compRepo.delete(comp); // delete company
		} else {
			throw new CompanyNotFoundException();
		}	
	}
	
	public void deleteCustomer(long customerId) throws CustomerNotFoundException {
		Customer cust = custRepo.findById(customerId)
				.orElseThrow(CustomerNotFoundException::new);
		
		cust.getCoupons().removeAll(cust.getCoupons());
		custRepo.save(cust);
		custRepo.delete(cust);
	}
	
	// ===================== get all methods ===================== \\
	
	public List<Company> getAllCompanies() {
		return compRepo.findAll();
	}
	
	public List<Customer> getAllCustomer() {
		return custRepo.findAll();
	}
	
	// ====================== get one methods ===================== \\
	
	public Company getOneCompany(long companyId) throws CompanyNotFoundException {
		return compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
	}
	
	public Customer getOneCustomer(long customerId) throws CustomerNotFoundException {
		return custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
	}
	

}
