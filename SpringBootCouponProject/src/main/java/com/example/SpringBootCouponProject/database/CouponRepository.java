package com.example.SpringBootCouponProject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringBootCouponProject.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	
	// add the methods that will help you remove excess loops in the facades.

}
