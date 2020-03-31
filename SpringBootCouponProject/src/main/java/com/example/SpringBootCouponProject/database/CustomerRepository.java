package com.example.SpringBootCouponProject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.SpringBootCouponProject.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	/*
	 * TODO merge the methods that needs to be merged. 
	 */
	
	boolean existsByEmail(String email);
	boolean existsByPassword(String password);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from BootCouponProject.customers_coupons where BootCouponProject.customers_customer_id")
	void deletePurchaseById(@Param("couponId") Integer couponId);

}
