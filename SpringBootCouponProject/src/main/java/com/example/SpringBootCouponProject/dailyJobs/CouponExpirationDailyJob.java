package com.example.SpringBootCouponProject.dailyJobs;

import java.sql.Date;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.SpringBootCouponProject.beans.Coupon;
import com.example.SpringBootCouponProject.beans.Customer;
import com.example.SpringBootCouponProject.database.CouponRepository;
import com.example.SpringBootCouponProject.database.CustomerRepository;

public class CouponExpirationDailyJob implements Runnable {

	/*
	 * I cant see here where you are deleting the customer purchases.
	 * TODO delete customer purchases?
	 */

	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomerRepository custRepo;
	private boolean quit;

	@Override
	public void run() {

		while (!quit) {
			Calendar cal = Calendar.getInstance();

			for (Coupon coupon : coupRepo.findAll()) { // run on all coupons.
				if (coupon.getEndDate().before(new Date(cal.getTimeInMillis()))) { // if the coupon date expired
					for (Customer cust : custRepo.findAll()) { // run on all customers <-- pointless
						for (Coupon coup : cust.getCoupons()) { // run on each customer's coupons <-- pointless
							if (coup.getCouponId() == coupon.getCouponId()) // if the the coupon has the same id as the
																			// coupon that expired <-- pointless
								coupRepo.delete(coup); // delete the customer's coupon <-- ????

						}
					}
					coupRepo.deleteById(coupon.getCouponId()); // delete coupon <-- you need to do this.
				}

			}
		}

		try {
			Thread.sleep(24 * 3600); // go to sleep for 24 hours
		} catch (InterruptedException e) {
			e.getMessage();
		}

	}

	public void stopJob() {
		quit = true;
	}

}
