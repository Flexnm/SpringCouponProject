package com.example.SpringBootCouponProject.facades;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.SpringBootCouponProject.beans.CategoryType;
import com.example.SpringBootCouponProject.beans.Company;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.facades.exceptions.CannotUpdateCouponIdOrCompanyIdException;
import com.example.SpringBootCouponProject.facades.exceptions.CompanyNotFoundException;
import com.example.SpringBootCouponProject.facades.exceptions.CouponExistsException;

@Service // Component
public class CompanyFacade extends ClientFacade {
	/**
	 * no need to auto wire the repositories
	 * because Client Facade has them all
	 * 
	 *
	 * TODO remove the unnessessary exceptions, see if the getCoupons() method returns the coupons list and use it.
	 * 	use the companyId that you get from the login method instead of the foreach loops to look for the company.
	 *	use the "findById()" method and see if you can get rid of foreach loops by adding findBy??? methods in the repos.
	 * */
	
	public long companyId;
	
	// ==================== login ===================== \\
	/*
	* You can create a method in the compRepo to find a company by email and password to simplify this method.
	*/
	
	public boolean login(String email, String password) {
		if(compRepo.existsByEmail(email) && compRepo.existsByPassword(password)) {
			for (Company comp : compRepo.findAll()) {
				if(comp.getEmail().equals(email) && comp.getPassword().equals(password)) {
					companyId = comp.getCompanyId();
					// if we got here, the method can return true.
				}
			}
			return false;
		}
		// here we failed to find a company with this email and password, so we need to return false.
		return true;
		// the goal is to see if the combination of email and password that was given to login are in the database.
		// you need to ask if the company with these email and password combination exists in the database, here you check if those exist at all and then ask if there is a company that has them together.
	}
	
	// =================== adding methods ================== \\
	/**
	 * 1. Can't add a coupon with the same title
	 * 
	 * */
	
	public void addCoupon(Coupon coupon) throws CompanyNotFoundException, CouponExistsException {
		Company comp = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		// no need to throw exception as this method, and other methods in this class, require the company to exist in the first place.
		for (Coupon coup : comp.getCoupons()) {
			if(coup.getTitle().equals(coupon.getTitle())) {
				throw new CouponExistsException();
			}
		}
		coupRepo.save(coupon);
	}
	
	// ================== update methods =================== \\
	/**
	 * 1. Can't update coupon id
	 * 
	 * 2. Can't update coupon company id 
	 * 
	 * */
	
	public void updateCoupon(Coupon coupon) throws CompanyNotFoundException, CannotUpdateCouponIdOrCompanyIdException {
		Company comp = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		for (Coupon coup : comp.getCoupons()) {
			if(coup.getCompany().getCompanyId() == coupon.getCompany().getCompanyId()
					&& coup.getCouponId() == coupon.getCouponId())
				// this "if" statement does nothing, to check the conditions 1 and 2 above you need to find the coupon that is going to be updated and 
				// see if what shouldnt be updated indeed was not unpdated. meaning, you need to compare the input coupon to a coupon with the same ID and see if it has the same company ID.
				coupRepo.save(coupon);
			else
				throw new CannotUpdateCouponIdOrCompanyIdException();
		}
	}
	
	// =================== delete methods =================== \\
	/**
	 * 1. delete all customer purchases as well
	 * 
	 * */
	// the method does not do what you want it to do. You mean to delete a coupon and customer purchases.
	public void deleteCoupon(long couponId) {
		for (Company comp : compRepo.findAll()) { // isnt needed, you are working with the current company, not other companies.
			if(comp.getCompanyId() == companyId) { // you can just do findById(companyId)
				for (Customer cust : custRepo.findAll()) {
					for (Coupon coup : cust.getCoupons()) {
						if(coup.getCouponId() == couponId)
							coupRepo.delete(coup);
				
					}
				}
			}			
		}
		coupRepo.deleteById(couponId);
	}
	
	// ================= get all methods =============== \\

	public List<Coupon> getAllCoupons() {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) { // use findById(companyId)
				for (Coupon coup : comp.getCoupons()) { /* if when you do findById(companyId) allows you to use .getCoupons() on the company object this whole method can just be:
									*  return compRepo.findById(companyId).getCoupons();
									*/
					coupons.add(coup);
				}
			}
		}
		return coupons;
	}
	
	// ================ get by methods ================== \\
	
	public List<Coupon> getCouponByCategory(CategoryType type) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) { // same as above
				for (Coupon coup : comp.getCoupons()) { // You dont need more than making the coupons arrayList and these 3 lines (and the return of course).
					if(coup.getType().equals(type)) {
						coupons.add(coup);
					}
				}
			}
		}
		return coupons;
	}
	
	public List<Coupon> getCouponsUpToPrice(double price) {
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company comp : compRepo.findAll()) {
			if(comp.getCompanyId() == companyId) {
				for (Coupon coup : comp.getCoupons()) { // same as above.
					if(coup.getPrice() <= price) {
						coupons.add(coup);
					}
				}
			}
		}
		return coupons;
	}
	
	// =================== get company details ================ \\
	
	public Company getCompanyDetails() throws CompanyNotFoundException {
		Company c = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		c.setCoupons(c.getCoupons()); // if getCoupons() returns the coupons in the database then no need to set it again (as it comes from the database with the coupons anyway).
		return c;
	}


}
