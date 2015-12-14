package com.by.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.AdminCouponForm;
import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.ParkingCoupon;
import com.by.service.ParkingCouponService;
import com.by.utils.PageUtils;

@Controller
@RequestMapping(value = "/admin/parkingCoupon")
public class AdminParkingCouponController {
	@Autowired
	private ParkingCouponService parkingCouponService;

	@RequestMapping(method = RequestMethod.POST)
	public String exchangeCoupon(AdminCouponForm form) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		Page<ParkingCoupon> lists = parkingCouponService.findFirstPage(10);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", PageUtils.computeLastPage(lists.getTotalPages()));
		return "admin/parkingCoupon/list";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(CouponQueryForm form,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponTemplateJson> json = parkingCouponService.findAll(form, pageable);
		return new Success<>(json);
	}
}
