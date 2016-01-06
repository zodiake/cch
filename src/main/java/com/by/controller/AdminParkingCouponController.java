package com.by.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.by.exception.NotFoundException;
import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.BaseCouponForm;
import com.by.json.CouponJson;
import com.by.json.CouponTemplateJson;
import com.by.model.Menu;
import com.by.model.ParkingCoupon;
import com.by.service.ParkingCouponService;

@Controller
@RequestMapping(value = "/admin/parkingCoupons")
public class AdminParkingCouponController extends BaseController {
	private final String CREATE = "admin/parkingCoupon/create";
	private final String EDIT = "admin/parkingCoupon/edit";
	private final String LIST = "admin/parkingCoupon/lists";
	private final String REDIRECT = "redirect:/admin/parkingCoupons/";
	private final Menu subMenu = new Menu(7);
	@Autowired
	private ParkingCouponService service;
	@Autowired
	private MessageSource messageSource;

	// 新增页
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		ParkingCoupon coupon = new ParkingCoupon();
		uiModel.addAttribute("coupon", coupon);
		addMenu(uiModel);
		return CREATE;
	}

	// 处理新增逻辑
	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("coupon") ParkingCoupon coupon, BindingResult result, Model uiModel,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			return CREATE;
		}
		ParkingCoupon source = service.save(coupon);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + source.getId() + "?edit";
	}

	// 获取修改
	@RequestMapping(value = "/{id}", params = "edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		ParkingCoupon coupon = service.findOne(id);
		if (coupon == null)
			throw new NotFoundException();
		uiModel.addAttribute("coupon", coupon);
		addMenu(uiModel);
		return EDIT;
	}

	// 处理修改
	@RequestMapping(value = "/{id}", params = "edit", method = RequestMethod.PUT)
	public String edit(@PathVariable("id") Long id, Model uiModel, @Valid ParkingCoupon coupon, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", coupon);
			uiModel.addAttribute("message", failMessage(messageSource));
			addMenu(uiModel);
			return EDIT;
		}
		service.update(coupon);
		addMenu(uiModel);
		redirectAttributes.addFlashAttribute("message", successMessage(messageSource));
		return REDIRECT + id + "?edit";
	}

	// 列表页
	@RequestMapping(method = RequestMethod.GET)
	public String list(BaseCouponForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponTemplateJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		addMenu(uiModel);
		return LIST;
	}

	// 页数使用json
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Status list(BaseCouponForm form,
			@PageableDefault(page = 0, size = 10, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<CouponTemplateJson> json = service.findAll(form, pageable);
		return new Success<>(json);
	}

	// 有效无效
	@RequestMapping(value = "/{id}/valid", method = RequestMethod.PUT)
	@ResponseBody
	public Status validOrNotValid(@PathVariable("id") int id) {
		ParkingCoupon coupon = new ParkingCoupon(id);
		return new Success<CouponJson>(new CouponJson(service.validOrInValid(coupon)));
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
