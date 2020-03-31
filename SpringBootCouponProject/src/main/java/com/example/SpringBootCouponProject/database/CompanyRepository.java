package com.example.SpringBootCouponProject.database;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringBootCouponProject.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	/*
	 * TODO merge the methods that needs to be merged. 
	 */

	boolean existsByEmail(String email);
	boolean existsByName(String name);
	boolean existsByPassword(String password);
//	Company findById(long companyId); // because findById returns Optional by default
}
