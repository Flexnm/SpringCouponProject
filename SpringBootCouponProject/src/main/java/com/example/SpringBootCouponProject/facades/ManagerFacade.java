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
				&& compRepo.existsByName(company.getName())) //  as said in the companyfacade, can merge both repo functions into 1.
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
				.orElseThrow(CompanyNotFoundException::new); // in this function we are expecting that the company object exists in thew database (we dont do new Company(...) when using this function).
		
		if(comp.getName().equals(company.getName()) 
				&& comp.getCompanyId() == company.getCompanyId()) // here you check if the company ID is the same, meaning that if you do new Company() then it will fail this condition here.
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
				.orElseThrow(CompanyNotFoundException::new); // same as above.
		
		if(compRepo.existsById(companyId)) { // this is another check if the company exists, again, not required as you delete a company that exists.
		for (Customer cust : custRepo.findAll()) {
			for (Coupon coup : cust.getCoupons()) {
				if(coup.getCompany().equals(comp))
						coupRepo.delete(coup); // all of this can be shortened with a method in couponRepository of findByCompanyId(long companyId); and then you just do a foreach on the list that it returns.
			}
		}
		comp.getCoupons().removeAll(comp.getCoupons());// delete all coupons <-- idk what this is supposed to do
		compRepo.save(comp); // do I have to update? <-- neither that.
		compRepo.delete(comp); // delete company
		} else {
			throw new CompanyNotFoundException(); // this exception should be thrown in this method, but you are checking if it exists twice.
		}
		// I dont see here where you delete all the customer purchases. lines 107 and 108 are not doing anything as you already deleting all the coupons in the loop above them.
	}
	
	public void deleteCustomer(long customerId) throws CustomerNotFoundException {
		Customer cust = custRepo.findById(customerId)
				.orElseThrow(CustomerNotFoundException::new); // same as above.
		
		cust.getCoupons().removeAll(cust.getCoupons()); 
		custRepo.save(cust);
		custRepo.delete(cust);
		// I dont know where you are deleting the purchases.
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
		return compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new); // this is fine to throw the exceptions in this method
	}
	
	public Customer getOneCustomer(long customerId) throws CustomerNotFoundException {
		return custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // this is fine to throw the exceptions in this method
	}
	

}
