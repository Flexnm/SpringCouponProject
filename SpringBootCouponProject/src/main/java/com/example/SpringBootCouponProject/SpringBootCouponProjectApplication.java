package com.example.SpringBootCouponProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootCouponProjectApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = 
				SpringApplication.run(SpringBootCouponProjectApplication.class, args);
		
		
	}

}
