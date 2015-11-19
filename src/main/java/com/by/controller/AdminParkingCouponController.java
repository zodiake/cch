package com.by.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.by.form.AdminCouponForm;

@Controller
@RequestMapping(value = "/admin/parkingCoupon")
public class AdminParkingCouponController {
	@RequestMapping(method = RequestMethod.POST)
	public String exchangeCoupon(AdminCouponForm form) {
		
		return null;
	}
}
