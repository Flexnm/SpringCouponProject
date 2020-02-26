package com.example.SpringBootCouponProject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringBootCouponProject.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	boolean existsByEmail(String email);
	boolean existsByPassword(String password);

}
