package com.example.SpringBootCouponProject.facades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;
import com.example.SpringBootCouponProject.facades.exceptions.CustomerNotFoundException;

@Service // Component
public class CustomerFacade extends ClientFacade {
	/**
	 * no need to auto wire the repositories
	 * because Client Facade has them all
	 * 
	 * */
	
	public long customerId;
	
	// ======================= login ====================== \\
	
	public boolean login(String email, String password) {
		if(custRepo.existsByEmail(email) && custRepo.existsByPassword(password)) {
			for (Customer cust : custRepo.findAll()) {
				if(cust.getEmail().equals(email) && cust.getPassword().equals(password)) {
					customerId = cust.getCustomerId();
				}
			}
			return false;
		}
		return true;
	}
	
	// ===================== purchase coupon ===================== \\
	/**
	 * 1. Can't purchase the same coupon twice
	 * 2. Can't purchase a coupon with amount 0
	 * 3. Can't purchase and out dated coupon
	 * 4. After the purchase set amount to amount -1
	 * 
	 * */
	
	public void PurchaseCoupon(Coupon coupon) throws CouponExistsException, CustomerNotFoundException {
		Calendar cal = Calendar.getInstance();
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // customer expected to exist in the first place.
		if(coupon.getAmount() > 0 && coupon.getEndDate().after(new Date(cal.getTimeInMillis()))) {
				for (Coupon coup : c.getCoupons()) {
					if(coup.getCouponId() == coupon.getCouponId()) {
						throw new CouponExistsException();
				
					}
				}
				c.getCoupons().add(coupon);
				custRepo.save(c);
				coupon.setAmount(coupon.getAmount()-1);
				coupRepo.save(coupon);
			}
     	}
	
	// =================== get all customer coupons ============== \\
	
	public List<Coupon> getAllCoupons() throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // customer expected to exist in the first place.
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) { // if c.getCoupons() already returns the coupons set, you can simply change the function to return a Set<Coupon> and just have the function be "return c.getCoupons();"
			coupons.add(coup);
		}
		return coupons;
	}
	
	// ====================== get coupons by ==================== \\
	
	public List<Coupon> getCouponsByCategory(CategoryType type) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // customer expected to exist in the first place.
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getType().equals(type))
			coupons.add(coup);
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsUpToPrice(double price) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // customer expected to exist in the first place.
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getPrice() == price) // supposed to be <=
			coupons.add(coup);
		}
		return coupons;
	}
	
	// =================== get customer details =====================\\
	
	public Customer getCustomerDetails() throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new); // customer expected to exist in the first place.
		c.setCoupons(c.getCoupons()); // if this has to be done, then the function of c.getCoupons() is not working at all. If this is doing nothing, it means that c.getCoupons() does its job and you get from the database a list of coupons already.
		return c;
	}
			
}
	


