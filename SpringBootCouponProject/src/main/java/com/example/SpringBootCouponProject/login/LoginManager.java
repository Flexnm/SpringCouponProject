package com.example.SpringBootCouponProject.login;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import com.example.SpringBootCouponProject.facades.ManagerFacade;
import com.example.SpringBootCouponProject.login.exceptions.AccessDeniedException;
import com.example.SpringBootCouponProject.facades.ClientFacade;
import com.example.SpringBootCouponProject.facades.CompanyFacade;
import com.example.SpringBootCouponProject.facades.CustomerFacade;

public class LoginManager {

	/*
	 * TODO use the annotations (@) to make this a singleton.
	 */

	@Autowired
	private ApplicationContext ctx;

	private static LoginManager instance = new LoginManager(); // a private var that we will only get by using a getter

	private LoginManager() {
	} // CTOR

	public static LoginManager getInstance() { // getter
		return instance;
	}

	// this is all fine:
	public ClientFacade login(String email, String password, ClientType type)
			throws SQLException, AccessDeniedException {

		switch (type) {

		case Admin:
			ManagerFacade mf = ctx.getBean(ManagerFacade.class);
			if (mf.login(email, password)) {
				return mf;
			} else {
				throw new AccessDeniedException();
			}

		case Company:
			CompanyFacade comf = ctx.getBean(CompanyFacade.class);
			if (comf.login(email, password)) {
				return comf;
			} else {
				throw new AccessDeniedException();
			}

		case Customer:
			CustomerFacade cusf = ctx.getBean(CustomerFacade.class);
			if (cusf.login(email, password)) {
				return cusf;
			} else {
				throw new AccessDeniedException();
			}

		}
		return null;

	}

}
