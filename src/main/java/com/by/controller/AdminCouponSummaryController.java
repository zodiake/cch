package com.by.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.model.Coupon;

@Controller
@RequestMapping(value = "/couponSummary")
public class AdminCouponSummaryController {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Coupon detail() {
		return null;
	}

}
