package com.by.controller;

import com.by.form.ShopCouponForm;
import com.by.json.ShopCouponJson;
import com.by.model.Menu;
import com.by.model.Shop;
import com.by.model.ShopCoupon;
import com.by.service.ShopCouponService;
import com.by.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
