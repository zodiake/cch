package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.model.CouponSummary;
import com.by.service.CouponSummaryService;

@Controller
@RequestMapping(value = "/couponSummary")
public class AdminCouponSummaryController {
	@Autowired
	private CouponSummaryService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CouponSummary detail() {
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public CouponSummary save(CouponSummary summary) {
		return service.save(summary);
	}
}
