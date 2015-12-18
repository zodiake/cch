package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.CouponQueryForm;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.model.ParkingCoupon;
import com.by.service.ParkingCouponService;
import com.by.utils.PageUtils;

@Controller
@RequestMapping(value = "/admin/parkingCoupon")
public class AdminParkingCouponController {
	private final String CREATE = "admin/parkingCoupon/create";
	private final String EDIT = "admin/parkingCoupon/edit";
	@Autowired
	private ParkingCouponService service;

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		ParkingCoupon coupon = new ParkingCoupon();
		uiModel.addAttribute("coupon", coupon);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String exchangeCoupon(@Valid @ModelAttribute("coupon") ParkingCoupon coupon, BindingResult result,
			Model uiModel, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			return CREATE;
		}
		ParkingCoupon source = service.save(coupon);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/admin/parkingCoupon/" + source.getId();
	}

	@RequestMapping(params = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model uiModel) {
		ParkingCoupon coupon = service.findOne(id);
		if (coupon == null)
			throw new NotFoundException();
		uiModel.addAttribute("coupon", coupon);
		return EDIT;
	}

	@RequestMapping(params = "/{id}", method = RequestMethod.POST)
	public String edit(@PathVariable("id") Long id, Model uiModel, @Valid ParkingCoupon coupon, BindingResult result) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			return EDIT;
		}
		service.update(coupon);
		return "redirect:/admin/parkingCoupon/" + id;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		Page<ParkingCoupon> lists = service.findFirstPage(10);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", PageUtils.computeLastPage(lists.getTotalPages()));
		return "admin/parkingCoupon/list";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(CouponQueryForm form,
			@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponTemplateJson> json = service.findAll(form, pageable);
		return new Success<>(json);
	}

	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	@ResponseBody
	public Status validOrNotValid(@PathVariable("id") Long id) {
		ParkingCoupon coupon = new ParkingCoupon(id);
		return new Success<CouponJson>(new CouponJson(service.validOrInValid(coupon)));
	}
}
