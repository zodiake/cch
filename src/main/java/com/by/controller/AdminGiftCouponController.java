package com.by.controller;

import com.by.exception.Status;
import com.by.exception.Success;
import com.by.form.BaseCouponForm;
import com.by.form.CouponQueryForm;
import com.by.json.CouponTemplateJson;
import com.by.model.Menu;
import com.by.service.GiftCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yagamai on 15-12-22.
 */
@Controller
@RequestMapping("/admin/giftCoupons")
public class AdminGiftCouponController extends BaseController {
    private final String CREATE = "admin/giftCoupons/create";
    private final String EDIT = "admin/giftCoupons/edit";
    private final String REDIRECT = "redirect:/admin/giftCoupons/";
    private final String LIST = "admin/giftCoupons/lists";
    private final Menu subMenu = new Menu(7);
    @Autowired
    private GiftCouponService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(BaseCouponForm form, Model uiModel,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "beginTime", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CouponTemplateJson> lists = service.findAll(form,
                new PageRequest(0, PAGE_SIZE, Sort.Direction.DESC, "beginTime"));
        uiModel.addAttribute("lists", lists);
        uiModel.addAttribute("last", computeLastPage(lists.getTotalPages()));
        uiModel.addAttribute("form", form);
        addMenu(uiModel);
        return LIST;
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Status list(CouponQueryForm form,
                       @PageableDefault(page = INIT_PAGE, size = PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return new Success<>(service.findAll(form, pageable));
    }

    @Override
    public Menu getSubMenu() {
        return subMenu;
    }
}
