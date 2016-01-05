package com.by.controller;

import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
import com.by.model.Menu;
import com.by.model.Message;
import com.by.model.Shop;
import com.by.model.ShopCoupon;
import com.by.service.ShopCouponService;
import com.by.service.ShopService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by yagamai on 16-1-4.
 */
@Controller
@RequestMapping(value = "/admin/shopCoupons")
public class AdminShopCouponController extends BaseController {
	private final String CREATE = "admin/shopCoupon/create";
	private final String EDIT = "admin/shopCoupon/edit";
	private final String LIST = "admin/shopCoupon/lists";
	private final String REDIRECT = "redirect:/admin/shopCoupons/";
	private final Menu subMenu = new Menu(9);
	@Autowired
	private ShopCouponService service;
	@Autowired
	private ShopService shopService;
	@Autowired
	private MessageSource messageSource;

	@ModelAttribute("shops")
	public List<Shop> shops() {
		return shopService.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	// 列表页
	@RequestMapping(method = RequestMethod.GET)
	public String list(ShopCouponForm form, Model uiModel,
			@PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<ShopCouponJson> lists = service.findAll(form, pageable);
		uiModel.addAttribute("lists", lists);
		uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
		uiModel.addAttribute("form", form);
		addMenu(uiModel);
		return LIST;
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String form(Model uiModel) {
		uiModel.addAttribute("coupon", new ShopCoupon());
		addMenu(uiModel);
		return CREATE;
	}

	@RequestMapping(params = "form", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("coupon") ShopCoupon shopCoupon, BindingResult result, Model uiModel) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			uiModel.addAttribute("coupon", shopCoupon);
			uiModel.addAttribute("message",
					new Message("fail", messageSource.getMessage("save.fail", new Object[] {}, Locale.CHINA)));
			addMenu(uiModel);
			return CREATE;
		}
		ShopCoupon coupon = service.save(shopCoupon);
		return REDIRECT + coupon.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") int id, Model uiModel) {
		ShopCoupon coupon = service.findOne(id);
		uiModel.addAttribute("coupon", coupon);
		addMenu(uiModel);
		return EDIT;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") int id, Model uiModel,
			@Valid @ModelAttribute("coupon") ShopCoupon shopCoupon, BindingResult result) {
		shopCoupon.setId(id);
		if (result.hasErrors()) {
			uiModel.addAttribute("coupon", shopCoupon);
			uiModel.addAttribute("message",
					new Message("fail", messageSource.getMessage("save.fail", new Object[] {}, Locale.CHINA)));
			addMenu(uiModel);
			return EDIT;
		}
		ShopCoupon coupon = service.update(shopCoupon);
		addMenu(uiModel);
		return REDIRECT + coupon.getId();
	}

	@Override
	public Menu getSubMenu() {
		return subMenu;
	}
}
